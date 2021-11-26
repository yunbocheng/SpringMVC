package com.yunbocheng.exception;

/**
 * @author : YunboCheng
 * @date : 11:14 2021/11/24
 */

/*
* 当输入的年龄存在异常的时候抛出异常
* */
public class AgeException extends MyUserException{
    public AgeException() {
        super();
    }

    public AgeException(String message) {
        super(message);
    }
}
