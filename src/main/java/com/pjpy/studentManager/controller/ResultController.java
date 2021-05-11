package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Award;
import com.pjpy.studentManager.domain.Result;
import com.pjpy.studentManager.service.ResultService;
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
@RequestMapping("/result")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @RequestMapping("/result_list")
    public String resultList(){
        return "result/resultList";
    }

    @PostMapping("/getResultList")
    @ResponseBody
    public Object getResultList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "rows",defaultValue = "100")Integer rows,
                                @RequestParam(value = "id",defaultValue = "0")Integer id,
                                String from){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("pageno",page);
        paraMap.put("pagesize",rows);
        if (id!=0) paraMap.put("id",id);
        PageBean<Result> pageBean = resultService.queryPage(paraMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String,Object> result = new HashMap<>();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }


}
