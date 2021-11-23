package com.yunbocheng.controller;

import com.yunbocheng.entity.Student;
import com.yunbocheng.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : YunboCheng
 * @date : 14:48 2021/11/22
 */

@Controller
@RequestMapping(value = "/student")
public class StudentController {

    // 使用自动注入的方式创建一个Service对象
    @Resource
    private StudentService service;


    // 注册学生
    @RequestMapping(value = "/addStudent.do")
    /*在形参的部分接收前端传递过来的参数，此时使用student对象接收参数*/
    public ModelAndView addStudent(Student student){
        ModelAndView mv = new ModelAndView();
        String str = "学生 【\" + student.getName() + \"】 注册失败！";
        int nums = service.addStudent(student);
        System.out.println(nums);
        if (nums == 1){
            // 注册成功
            str = "学生 【" + student.getName() + "】 注册成功！";
        }
        // 添加数据
        mv.addObject("result",str);
        // 指定结果页面
        mv.setViewName("show");
        return mv;
    }

    /*使用JSTL核心标签库中的forEach标签在显示jsp页面上循环遍历这个list集合*/
    @RequestMapping(value = "/selectStudent.do")
    public ModelAndView selectStudent(){
        ModelAndView mv = new ModelAndView();
        List<Student> student = service.findStudent();
        mv.addObject("list",student);
        mv.setViewName("showSelect");
        return mv;
    }

    /*
    * 使用ajax请求来获取全部的学生对象。
    * 处理器方法的返回值为List<Object>
    * 当返回值是数据的时候（List<Object>、Object、String字符串），需要加上 @ResponseBody 注解
    * 当返回的而是ModelAndView、String(跳转页面)、void的时候不需要添加 @ResponseBody 注解
     * */
    @RequestMapping(value = "/selectAll.do")
    @ResponseBody
    public List<Student> selectAll(){
        List<Student> list = service.findStudent();
        return list;
    }
}
