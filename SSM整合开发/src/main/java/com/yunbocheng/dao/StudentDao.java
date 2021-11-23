package com.yunbocheng.dao;

import com.yunbocheng.entity.Student;

import java.util.List;

/**
 * @author : YunboCheng
 * @date : 11:42 2021/11/22
 */
public interface StudentDao {

    // 录入学生信息
    int insertStudent(Student student);

    // 查询全部的学生信息
    List<Student> selectStudent();
}
