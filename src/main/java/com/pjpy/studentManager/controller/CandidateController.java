package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Candidate;
import com.pjpy.studentManager.service.CandidateService;
import com.pjpy.studentManager.service.ScoreService;
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
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ScoreService scoreService;

    @RequestMapping("/candidate_list")
    public String CandidateList(){
        return "score/candidate";
    }

    @PostMapping("getCandidateList")
    @ResponseBody
    public Object getCandidateList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                   @RequestParam(value = "rows",defaultValue = "100")Integer rows,
                                   @RequestParam(value = "id",defaultValue = "0")Integer id,
                                   String from){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("pageno",page);
        paraMap.put("pagesize",rows);
        if (id!=0) paraMap.put("id",id);
        PageBean<Candidate> pageBean = candidateService.queryPage(paraMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else {
            Map<String,Object> result = new HashMap<>();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }

    @PostMapping("/addCandidate")
    @ResponseBody
    public AjaxResult addCandidate(@RequestParam(value = "level")String level){
        double s = Double.parseDouble(level);
        Candidate sn = scoreService.getCandidate(s);
        sn.setUsername(scoreService.getName(sn.getSn()));
        AjaxResult ajaxResult = new AjaxResult();
        int count = candidateService.addCandidate(sn);
        if(count > 0){
            ajaxResult.setSuccess(true);
            ajaxResult.setMessage("录入成功");
        }else{
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("系统错误，请重新录入");
        }
        return ajaxResult;
    }

    @PostMapping("/isCandidate")
    @ResponseBody
    public AjaxResult isCandidate(String sn){
        AjaxResult ajaxResult = new AjaxResult();
        if(candidateService.isCandidate(sn)){
            ajaxResult.setSuccess(true);
        }else{
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("抱歉，你没有申请资格");
        }
        return ajaxResult;
    }
}
