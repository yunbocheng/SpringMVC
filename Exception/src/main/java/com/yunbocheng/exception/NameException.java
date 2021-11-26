package com.yunbocheng.exception;

/**
 * @author : YunboCheng
 * @date : 11:13 2021/11/24
 */
/*
* 当用户的姓名存在异常的时候抛出的异常，抛出NameException
* */
public class NameException extends MyUserException{
    public NameException() {
        super();
    }

    public NameException(String message) {
        super(message);
    }
}
