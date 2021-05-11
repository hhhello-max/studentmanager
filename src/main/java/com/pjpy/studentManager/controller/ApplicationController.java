package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Application;
import com.pjpy.studentManager.domain.Result;
import com.pjpy.studentManager.domain.Student;
import com.pjpy.studentManager.mapper.CandidateMapper;
import com.pjpy.studentManager.service.ApplicationService;
import com.pjpy.studentManager.service.CandidateService;
import com.pjpy.studentManager.service.ResultService;
import com.pjpy.studentManager.service.StudentService;
import com.pjpy.studentManager.util.AjaxResult;
import com.pjpy.studentManager.util.PageBean;
import com.pjpy.studentManager.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ResultService resultService;

    @RequestMapping("/application_list")
    public String ApplicationList(){
        return "/award/record";
    }

    @PostMapping("/getApplicationList")
    @ResponseBody
    public Object getApplicationList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                     @RequestParam(value = "rows",defaultValue = "100")Integer rows,
                                     @RequestParam(value = "id",defaultValue = "0")Integer id,
                                     String from){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("pageno",page);
        paraMap.put("pagesize",rows);
        if (id!=0) paraMap.put("id",id);
        PageBean<Application> pageBean = applicationService.queryPage(paraMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String,Object> result = new HashMap<>();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }

    @PostMapping("/getApplication")
    @ResponseBody
    public Object getApplication(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                 @RequestParam(value = "rows",defaultValue = "100")Integer rows,
                                 @RequestParam(value = "id",defaultValue = "0")Integer id,
                                 String from, HttpSession session){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("pageno",page);
        paraMap.put("pagesize",rows);
        if (id!=0) paraMap.put("id",id);

        System.out.println("执行到这里了");
        Student student = (Student) session.getAttribute("student");
        String sn = student.getSn();

        PageBean<Application> pageBean = applicationService.queryBySn(paraMap,sn);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String,Object> result = new HashMap<>();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }



    @PostMapping("/addApplication")
    @ResponseBody
    public AjaxResult addApplication(@RequestParam("file") MultipartFile[] files, Application application){
        AjaxResult ajaxResult = new AjaxResult();
        File fileDir = UploadUtil.getImgDirFile();
        for (MultipartFile fileImg : files){
            // 拿到文件名
            String extName = fileImg.getOriginalFilename().substring(fileImg.getOriginalFilename().lastIndexOf("."));
            String uuidName = UUID.randomUUID().toString();

            try {
                // 构建真实的文件路径
                File newFile = new File(fileDir.getAbsolutePath() + File.separator +uuidName+ extName);

                // 上传图片到 -》 “绝对路径”
                fileImg.transferTo(newFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            application.setMaterial(uuidName+extName);
        }
        try {
            int count = applicationService.addApplication(application);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("添加成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常，请重试");
        }
        return ajaxResult;
    }

    @PostMapping("/checkApplication")
    @ResponseBody
    public AjaxResult checkApplication(Application application){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = applicationService.checkApplication(application);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("审批成功");
                Result result = new Result();

                String sn = application.getSn();
                double cg = candidateService.getGrade(sn);
                double ag = application.getGrade();

                result.setSn(sn);
                result.setUsername(application.getApplicant());
                result.setClazz(studentService.getClazz(sn));
                result.setAward(application.getAward());
                result.setGrade(ag+cg);
                resultService.addResult(result);
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("审批失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常，请重试");
        }
        return ajaxResult;
    }

    @PostMapping("/deleteApplication")
    @ResponseBody
    public AjaxResult deleteApplication(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(id);
        try {
            int count = applicationService.deleteApplication(id);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统异常，请重试");
        }
        return ajaxResult;
    }

}
