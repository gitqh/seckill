package org.gitqh.seckill.exception;

/**
 * @author qh
 * @create 2016-06-19-23:15
 */
public class SeckillException extends RuntimeException {
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
