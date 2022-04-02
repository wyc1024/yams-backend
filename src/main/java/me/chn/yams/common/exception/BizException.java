package me.chn.yams.common.exception;

/**
 * 系统内部异常
 *
 * @author chn
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

}
