package com.yunbocheng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : YunboCheng
 * @date : 11:41 2021/11/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    // 属性名和数据库的列名一致，如果不一样的话需要使用resultMap进行修改
    private String num;

    private String province;

    private String city;

    private String name;

    private Integer age;

    private Double score;

    private String room;
}
