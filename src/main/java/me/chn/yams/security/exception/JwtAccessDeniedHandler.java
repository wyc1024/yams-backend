package me.chn.yams.security.exception;

import me.chn.yams.common.entity.R;
import me.chn.yams.common.kit.WebKit;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shuang.kou
 * @description AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 当用户尝试访问需要权限才能的REST资源而权限不足的时候，
     * 将调用此方法发送403响应以及错误信息
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        WebKit.writeJson(new R().msg("权限不足").toString(), response);
    }

}
