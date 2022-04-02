package me.chn.yams.common.exception;

import lombok.extern.slf4j.Slf4j;
import me.chn.yams.common.entity.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity handleException(Exception e) {
//        log.error("系统内部异常", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new R().msg("系统内部异常"));
//    }

    @ExceptionHandler(value = BizException.class)
    public ResponseEntity handleBizException(BizException e) {
        log.error("系统业务异常", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new R().msg(e.getMessage()));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HTTP 请求体解析异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new R().msg("HTTP 请求体解析异常"));
    }

    /**
     * 统一处理请求参数校验(实体对象传参-form)
     *
     * @param e BindException
     * @return R
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(BindException e) {
        log.error("参数校验异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new R().msg(e.getMessage()));
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return R
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e) {
        log.error("参数校验异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new R().msg(e.getMessage()));
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return R
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new R().msg(e.getMessage()));
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = String.format("该方法不支持%s请求", StringUtils.substringBetween(e.getMessage(), "'", "'"));
        log.error(message, e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new R().msg(message));
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件大小超出限制", e);
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(new R().msg("文件大小超出限制"));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity handleAuthenticationException(AuthenticationException e) {
        log.error("认证失败", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new R().msg("认证失败"));
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限不足", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new R().msg("权限不足"));
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("存在重复数据", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new R().msg(e.getMessage()));
    }

}
