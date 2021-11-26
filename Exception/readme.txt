异常处理
SpringMVC框架采用的是统一，全局的异常处理。
把Controller中的所有异常处理都集中到一个地方。采用的是AOP切面编程思想。
把业务逻辑和异常处理的代码分开。解耦合。
处理异常的步骤:
    1.新建Maven项目
    2.加入依赖
    3.新建一个自定义异常类 MyUserException，在定义它的子类NameException、AgeException
    4.在Controller类中抛出异常。
    5.创建一个普通类，作为全局异常处理类。
      1) 在类的上面加入@ControllerAdvice注解
      2) 在类定义方法，方法的上面加入@ExceptionHandler

   6. 创建处理异常类视图界面
   7. 创建SpringMVC的配置文件
       1) 组件扫描器，扫描@Controller注解。
       2) 组件扫描器，扫描@ControllerAdvice所在的包。
       3) 声明注解驱动。

