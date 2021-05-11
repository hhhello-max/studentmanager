package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Admin;
import com.pjpy.studentManager.domain.Inform;
import com.pjpy.studentManager.domain.Student;
import com.pjpy.studentManager.domain.Teacher;
import com.pjpy.studentManager.service.AdminService;
import com.pjpy.studentManager.service.InformService;
import com.pjpy.studentManager.service.StudentService;
import com.pjpy.studentManager.service.TeacherService;
import com.pjpy.studentManager.util.AjaxResult;
import com.pjpy.studentManager.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private InformService informService;

    /**
     * 跳转登录界面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    @GetMapping("/informList")
    public String informList(){
        return "system/informList";
    }

    /**
     * 登录表单提交 校验
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult submitlogin(String username, String password, String type,
                                  HttpSession session){
        AjaxResult ajaxResult = new AjaxResult();

        if(StringUtils.isEmpty(username)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请填写用户名");
            return ajaxResult;
        }
        if(StringUtils.isEmpty(password)){
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("请填写密码");
            return ajaxResult;
        }
        //数据库校验
        switch (type){
            case "1":{ //管理员
                Admin admin = new Admin();
                admin.setPassword(password);
                admin.setUsername(username);
                Admin ad = adminService.findByAdmin(admin);
                if(StringUtils.isEmpty(ad)){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("用户名或密码错误");
                    return ajaxResult;
                }
                ajaxResult.setSuccess(true);
                session.setAttribute(Const.ADMIN,ad);
                session.setAttribute(Const.USERTYPE,"1");
                break;
            }
            case "2":{
                Student student = new Student();
                student.setPassword(password);
                student.setUsername(username);
                Student st = studentService.findByStudent(student);
                if(StringUtils.isEmpty(st)){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("用户名或密码错误");
                    return ajaxResult;
                }
                ajaxResult.setSuccess(true);
                session.setAttribute(Const.STUDENT,st);
                session.setAttribute(Const.USERTYPE,"2");
                break;
            }
            case "3":{
                Teacher teacher = new Teacher();
                teacher.setPassword(password);
                teacher.setUsername(username);
                Teacher tr = teacherService.findByTeacher(teacher);
                if(StringUtils.isEmpty(tr)){
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("用户名或密码错误");
                    return ajaxResult;
                }
                ajaxResult.setSuccess(true);
                session.setAttribute(Const.TEACHER,tr);
                session.setAttribute(Const.USERTYPE,"3");
                break;
            }
        }

        List<Inform> informs = informService.getAll();
        session.setAttribute("informs",informs);

        return ajaxResult;
    }

    /**
     * 跳转后台主页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "/system/index";
    }


    /**
     * 登出
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "/login";
    }






    @GetMapping("/personalView")
    public String personalView(){
        return "/system/personalView";
    }


    /**
     * 修改密码
     * @param password
     * @param newpassword
     * @param session
     * @return
     */
    @PostMapping("/editPassword")
    @ResponseBody
    public AjaxResult editPassword(String password,String newpassword,HttpSession session){
        AjaxResult ajaxResult = new AjaxResult();
        String usertype = (String) session.getAttribute(Const.USERTYPE);
        if (usertype.equals("1")){
            //管理员
            Admin admin = (Admin)session.getAttribute(Const.ADMIN);
            if(!password.equals(admin.getPassword())){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("原密码错误");
                return ajaxResult;
            }
            admin.setPassword(newpassword);
            try{
                int count = adminService.editPswdByAdmin(admin);
                if(count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("修改成功,请重新登录");
                }else{
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("修改失败");
                }
            }catch (Exception e){
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        }
        if(usertype.equals("2")){
            //学生
            Student student = (Student)session.getAttribute(Const.STUDENT);
            if(!password.equals(student.getPassword())){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("原密码错误");
                return ajaxResult;
            }
            student.setPassword(newpassword);
            try{
                int count = studentService.editPswdByStudent(student);
                if(count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("修改成功,请重新登录");
                }else{
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("修改失败");
                }
            }catch (Exception e){
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        }
        if(usertype.equals("3")){
            //教师
            Teacher teacher = (Teacher) session.getAttribute(Const.TEACHER);
            if(!password.equals(teacher.getPassword())){
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("原密码错误");
                return ajaxResult;
            }
            teacher.setPassword(newpassword);
            try{
                int count = teacherService.editPswdByTeacher(teacher);
                if(count > 0){
                    ajaxResult.setSuccess(true);
                    ajaxResult.setMessage("修改成功,请重新登录");
                }else{
                    ajaxResult.setSuccess(false);
                    ajaxResult.setMessage("修改失败");
                }
            }catch (Exception e){
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        }
        return ajaxResult;
    }

}
