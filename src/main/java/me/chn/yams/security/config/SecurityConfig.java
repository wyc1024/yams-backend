package me.chn.yams.security.config;

import me.chn.yams.security.constant.SecurityConstant;
import me.chn.yams.security.exception.JwtAccessDeniedHandler;
import me.chn.yams.security.exception.JwtAuthenticationEntryPoint;
import me.chn.yams.security.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                // 禁用 CSRF
                .csrf().disable()
                // 防止 web 页面的 Frame 被拦截
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // 指定的接口直接放行
                .antMatchers(SecurityConstant.SWAGGER_WHITELIST).permitAll()
                .antMatchers(SecurityConstant.SYSTEM_WHITELIST).permitAll()
                // 其他的接口都需要认证后才能请求
                .anyRequest().authenticated()
                .and()
                // 添加自定义Filter
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                // 不需要 session（不创建会话）
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 授权异常处理
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler);
    }

}
