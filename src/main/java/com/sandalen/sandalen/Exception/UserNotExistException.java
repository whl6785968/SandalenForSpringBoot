package com.sandalen.sandalen.Exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(){
        super("用户不存在");
    }
}
