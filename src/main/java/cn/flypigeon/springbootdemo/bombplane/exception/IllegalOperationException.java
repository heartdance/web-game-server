package cn.flypigeon.springbootdemo.bombplane.exception;

/**
 * Created by htf on 2020/10/16.
 */
public class IllegalOperationException extends RuntimeException {
    public IllegalOperationException(String message) {
        super(message);
    }
}
