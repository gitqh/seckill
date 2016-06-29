package org.gitqh.seckill.exception;

/**
 * @author qh
 * @create 2016-06-19-23:16
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
