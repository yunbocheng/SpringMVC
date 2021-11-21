package com.yunbocheng.entity;

import com.fasterxml.jackson.core.JsonFactory;

/**
 * @author : YunboCheng
 * @date : 17:19 2021/11/19
 */

/*
* 这个类是用来接收用户请求参数的一个普通类
* */
public class Student {
    // 属性名和请求中参数名必须保持一致
    private String name;
    private Integer age;

    public Student() {
        System.out.println("无参构造方法！");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName=" + name);
        this.name = name;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        System.out.println("setAge=" + age);
        this.age = age;
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
