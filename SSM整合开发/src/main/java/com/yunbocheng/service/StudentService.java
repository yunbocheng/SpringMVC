package com.yunbocheng.service;

import com.yunbocheng.entity.Student;

import java.util.List;

/**
 * @author : YunboCheng
 * @date : 11:58 2021/11/22
 */
public interface StudentService {

    // 录入学生信息
    int addStudent(Student student);

    // 查询全部学生信息
    List<Student> findStudent();
}
