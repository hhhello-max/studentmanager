package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Course;
import com.pjpy.studentManager.domain.Inform;
import com.pjpy.studentManager.domain.Score;
import com.pjpy.studentManager.service.InformService;
import com.pjpy.studentManager.util.AjaxResult;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/inform")
public class InformController {

    @Autowired
    private InformService informService;

    @PostMapping("/getInformList")
    @ResponseBody
    public Object getInformList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "rows",defaultValue = "100")Integer rows,
                                @RequestParam(value = "id",defaultValue = "0")Integer id,
                                String from){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("pageno",page);
        paraMap.put("pagesize",rows);
        if (id!=0) paraMap.put("id",id);
        PageBean<Inform> pageBean = informService.queryPage(paraMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String,Object> result = new HashMap<>();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }


    @PostMapping("/addInform")
    @ResponseBody
    public AjaxResult addInform(Inform inform){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = informService.addInform(inform);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("添加成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("添加失败");
        }
        return ajaxResult;
    }

    /**
     * 修改通知
     * @param inform
     * @return
     */
    @PostMapping("/editInform")
    @ResponseBody
    public AjaxResult editInform(Inform inform){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(inform.toString());
        try {
            int count = informService.editInform(inform);
            if(count > 0){
                //签到成功
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新修改");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统错误，请重新修改");
        }

        return ajaxResult;
    }

    /**
     * 删除通知
     * @param id
     * @return
     */
    @PostMapping("/deleteInform")
    @ResponseBody
    public AjaxResult deleteInform(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = informService.deleteInform(id);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("删除成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统错误，请重新删除");
        }

        return ajaxResult;
    }

}
