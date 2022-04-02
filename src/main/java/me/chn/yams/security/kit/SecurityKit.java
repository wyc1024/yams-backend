package me.chn.yams.security.kit;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import me.chn.yams.common.exception.BizException;
import me.chn.yams.common.kit.WebKit;
import me.chn.yams.security.constant.SecurityConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public class SecurityKit {

    public static boolean hasAuthority(String authority) {
        if (StringUtils.isBlank(authority)) return false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (CollectionUtils.isEmpty(authorities)) return false;
        return authorities.stream().anyMatch(authority::equals);
    }

    public static void checkAuthority(String authority) {
        SecurityKit.checkAuthority(authority, "没有权限");
    }

    public static void checkAuthority(String authority, String expMsg) {
        if (!SecurityKit.hasAuthority(authority)) {
            throw new AccessDeniedException(expMsg);
        }
    }

    public static Long getUserId() {
        HttpServletRequest request = WebKit.getRequest();
        if (request == null) return null;
        String token = request.getHeader(SecurityConstant.TOKEN_HEADER);
        if (StringUtils.isBlank(token)) return null;
        JWT jwt = SecurityKit.parseToken(token);
        Object userId = jwt.getPayload("id");
        if (userId == null) return null;
        return Long.valueOf(userId.toString());
    }

    public static JWT parseToken(String token) {
        JWT jwt;
        try {
            jwt = JWT.of(token).setKey(SecurityConstant.JWT_SECRET);
        } catch (JWTException e) {
            throw new BizException("Token 解析异常", e);
        }
        return jwt;
    }

}
