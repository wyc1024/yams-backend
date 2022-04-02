package me.chn.yams.security.filter;

import cn.hutool.jwt.JWT;
import me.chn.yams.common.exception.BizException;
import me.chn.yams.security.constant.SecurityConstant;
import me.chn.yams.security.kit.SecurityKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author shuang.kou
 * @description 过滤器处理所有HTTP请求，并检查是否存在带有正确令牌的Authorization标头。例如，如果令牌未过期或签名密钥正确。
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstant.TOKEN_HEADER);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        JWT jwt = SecurityKit.parseToken(token);
        Object username = jwt.getPayload("username");
        Long expiresAt = (Long) jwt.getPayload("expiresAt");
        String authorities = (String) jwt.getPayload("authorities");
        if (username == null || expiresAt == null) throw new BizException("Token 无效");
        if (System.currentTimeMillis() > expiresAt) throw new BizException("Token 已过期");
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = null;
        if (StringUtils.isNotBlank(authorities)) {
            simpleGrantedAuthorities = Arrays.asList(authorities.split(",")).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
