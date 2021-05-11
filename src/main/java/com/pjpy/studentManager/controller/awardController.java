package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Award;
import com.pjpy.studentManager.service.AwardService;
import com.pjpy.studentManager.util.AjaxResult;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/award")
public class awardController {

    @Autowired
    private AwardService awardService;

    @RequestMapping("/award_list")
    public String AwardList(){
        return "/award/awardList";
    }

    @RequestMapping("/stu_award_list")
    public String stuAwardList(){
        return "/award/stu_awardList";
    }

    @RequestMapping("/stu_record")
    public String stuRecord(){
        return "award/stu_record";
    }

    @PostMapping("getAwardList")
    @ResponseBody
    public Object getAwardList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                               @RequestParam(value = "rows",defaultValue = "100")Integer rows,
                               @RequestParam(value = "id",defaultValue = "0")Integer id,
                               String from){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("pageno",page);
        paraMap.put("pagesize",rows);
        if (id!=0) paraMap.put("id",id);
        PageBean<Award> pageBean = awardService.queryPage(paraMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String,Object> result = new HashMap<>();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }

    @PostMapping("/addAward")
    @ResponseBody
    public AjaxResult addAward(Award award){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = awardService.addAward(award);
            if (count>0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("保存成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("保存失败");
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;

    }

    @PostMapping("/editAward")
    @ResponseBody
    public AjaxResult editAward(Award award){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = awardService.editAward(award);
            if (count>0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("保存成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("保存失败");
        }
        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    @PostMapping("/deleteAward")
    @ResponseBody
    public AjaxResult deleteLeave(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(id);
        try {
            int count = awardService.deleteAward(id);
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

