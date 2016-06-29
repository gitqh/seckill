package org.gitqh.seckill.exception;

/**
 * @author qh
 * @create 2016-06-19-23:18
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
