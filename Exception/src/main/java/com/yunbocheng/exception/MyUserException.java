package com.yunbocheng.exception;

/**
 * @author : YunboCheng
 * @date : 11:11 2021/11/24
 */

/*
* 这是我们自己创建的异常类
* */
public class MyUserException extends Exception{

    /*继承父类的有参构造个无参构造方法*/

    public MyUserException() {
        super();
    }

    public MyUserException(String message) {
        super(message);
    }
}
