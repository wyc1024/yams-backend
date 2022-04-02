package me.chn.yams.security.constant;

/**
 * TODO: 移到配置文件中
 */
public class SecurityConstant {

    public static final String TOKEN_HEADER = "token";
    public static final byte[] JWT_SECRET = "secret.2000.01.01.!@#".getBytes();
    // 过期时间8小时
    public static final long EXPIRES_TIME = 8*60*60*1000;
    public static final String ROLE_PREFIX = "ROLE_";
    // Swagger WHITELIST
    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**",
            //knife4j
            "/doc.html",
    };
    // System WHITELIST
    public static final String[] SYSTEM_WHITELIST = {
            "/user/token",
    };

}
