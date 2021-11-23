package com.yunbocheng.service.impl;

import com.yunbocheng.dao.StudentDao;
import com.yunbocheng.entity.Student;
import com.yunbocheng.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : YunboCheng
 * @date : 11:59 2021/11/22
 */

/*声明Service对象*/
@Service
public class StudentServiceImpl implements StudentService {

    /*
        以前我们使用的是手动注入，在spring主配置文件中使用set手动注入。
        这里使用 引用类型的自动注入@Autowired,@Resource
        spring只能给引用类型的数据自动赋值，不可以给引用类型的数据赋值。
        @Resource默认使用的是byName，不成功的时候使用ByType
    */
    /*
        dao接口的首字母小写。这种使用的就是@resource的byName的方式
        @Resource(name = "studentDao")
    */
    /*
    * 这种使用的就是@Resource的byType方式
    * 使用自动注入的方式创建一个StudentDao对象
    * */
    @Resource
    private StudentDao studentDao;

    @Override
    public int addStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    @Override
    public List<Student> findStudent() {
        return studentDao.selectStudent();
    }
}
