package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Score;
import com.pjpy.studentManager.domain.Student;
import com.pjpy.studentManager.service.CourseService;
import com.pjpy.studentManager.service.ScoreService;
import com.pjpy.studentManager.service.StudentService;
import com.pjpy.studentManager.util.AjaxResult;
import com.pjpy.studentManager.util.Const;
import com.pjpy.studentManager.util.PageBean;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;


    @GetMapping("/score_list")
    public String scoreList(){
        return "/score/scores";
    }


    /**
     * 异步加载成绩数据列表
     * @param page
     * @param rows
     * @param from
     * @param session
     * @return
     */
    @RequestMapping("/getScoreList")
    @ResponseBody
    public Object getScoreList(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "100")Integer rows,
                               @RequestParam(value = "sn", defaultValue = "0")String sn,
                               String from, HttpSession session){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("pageno",page);
        paramMap.put("pagesize",rows);
        if(!sn.equals("0"))  paramMap.put("sn",sn);
//
//        //判断是老师还是学生权限
//        Student student = (Student) session.getAttribute(Const.STUDENT);
//        if(!StringUtils.isEmpty(student)){
//            //是学生权限，只能查询自己的信息
//            paramMap.put("sn",student.getSn());
//        }
        PageBean<Score> pageBean = scoreService.queryPage(paramMap);
        if(!StringUtils.isEmpty(from) && from.equals("combox")){
            return pageBean.getDatas();
        }else{
            Map<String,Object> result = new HashMap();
            result.put("total",pageBean.getTotalsize());
            result.put("rows",pageBean.getDatas());
            return result;
        }
    }


    /**
     * 添加成绩
     * @param score
     * @return
     */
    @PostMapping("/addScore")
    @ResponseBody
    public AjaxResult addScore(Score score){
        AjaxResult ajaxResult = new AjaxResult();
        //判断是否已录入成绩
        if(scoreService.isScore(score)){
            //true为已签到
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("已录入，请勿重复录入！");
        }else{
            int count = scoreService.addScore(score);
            if(count > 0){
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("录入成功");
            }else{
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("系统错误，请重新录入");
            }
        }
        return ajaxResult;
    }


    /**
     * 修改学生成绩
     * @param score
     * @return
     */
    @PostMapping("/editScore")
    @ResponseBody
    public AjaxResult editScore(Score score){
        AjaxResult ajaxResult = new AjaxResult();
        System.out.println(score.toString());
        try {
            int count = scoreService.editScore(score);
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
     * 删除学生成绩
     * @param id
     * @return
     */
    @PostMapping("/deleteScore")
    @ResponseBody
    public AjaxResult deleteScore(Integer id){
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = scoreService.deleteScore(id);
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

    /**
     * 导入xlsx表 并存入数据库
     * @param importScore
     * @param response
     */
    @PostMapping("/importScore")
    @ResponseBody
    public AjaxResult importScore(@RequestParam("importScore") MultipartFile importScore, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        AjaxResult ajaxResult = new AjaxResult();
        try {
            InputStream inputStream = importScore.getInputStream();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);

            String sn;
            String course;
            double grade;
            double credit = 0;
            double point = 0;

            int count = 0;
            String errorMsg = "";
            for(int rowNum = 1; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
                XSSFRow row = sheetAt.getRow(rowNum); // 获取第rowNum行
                //第0列
                XSSFCell cell = row.getCell(0); // 获取第rowNum行的第0列 即坐标（rowNum，0）
                if (cell == null) {
                    errorMsg += "第" + rowNum + "行学号缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    sn = cell.getStringCellValue();
                }

                //第1列
                cell = row.getCell(1);
                if (cell == null) {
                    errorMsg += "第" + rowNum + "行课程缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    course = cell.getStringCellValue();
                }

                //
                cell = row.getCell(2);
                if (cell == null) {
                    errorMsg += "第" + rowNum + "行年龄缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    grade = cell.getNumericCellValue();
                }

                cell = row.getCell(3);
                if (cell != null){
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    credit = cell.getNumericCellValue();
                }

                cell = row.getCell(4);
                if (cell != null){
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    point = cell.getNumericCellValue();
                }

                Score score = new Score();
                score.setSn(sn);
                score.setCourse(course);
                score.setGrade(grade);
                score.setCredit(credit);
                score.setPoint(point);

                int i = scoreService.addScore(score);
                if(i > 0){
                    count ++ ;
                }

            }
            errorMsg += "成功导入" + count + "条成绩！";
            ajaxResult.setSuccess(true);
            ajaxResult.setMessage(errorMsg);
        }catch (IOException e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("上传错误");
        }
        return ajaxResult;
    }


    /**
     * 导出xlsx表
     * @param response
     * @param score
     * @param session
     */
    @RequestMapping("/exportScore")
    @ResponseBody
    private void exportScore(HttpServletResponse response,Score score,HttpSession session) {
        //获取当前登录用户类型
        Student student = (Student) session.getAttribute(Const.STUDENT);
        if(!StringUtils.isEmpty(student)){
            //如果是学生，只能查看自己的信息
            score.setSn(student.getSn());
        }
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("score_list_sid_"+score.getSn()+"_cid_"+score.getSn()+".xls", "UTF-8"));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            ServletOutputStream outputStream = response.getOutputStream();
            List<Score> scoreList = scoreService.getAll(score);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet createSheet = xssfWorkbook.createSheet("成绩列表");
            XSSFRow createRow = createSheet.createRow(0);
            createRow.createCell(0).setCellValue("学生");
            createRow.createCell(1).setCellValue("课程");
            createRow.createCell(2).setCellValue("成绩");
            createRow.createCell(3).setCellValue("备注");
            //实现将数据装入到excel文件中
            int row = 1;
            for( Score s:scoreList){
                createRow = createSheet.createRow(row++);
//                createRow.createCell(0).setCellValue(s.getStudentName());
//                createRow.createCell(1).setCellValue(s.getCourseName());
//                createRow.createCell(2).setCellValue(s.getScore());
//                createRow.createCell(3).setCellValue(s.getRemark());
            }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计成绩数据
     * @param courseid
     * @param searchType
     * @return
     */
//    @RequestMapping("/getScoreStatsList")
//    @ResponseBody
//    public Object getScoreStatsList(@RequestParam(value = "courseid", defaultValue = "0")Integer courseid,
//                                        String searchType){
//        AjaxResult ajaxResult = new AjaxResult();
//        if(searchType.equals("avg")){
//            ScoreStats scoreStats = scoreService.getAvgStats(courseid);
//
//            List<Double> scoreList = new ArrayList<Double>();
//            scoreList.add(scoreStats.getMax_score());
//            scoreList.add(scoreStats.getMin_score());
//            scoreList.add(scoreStats.getAvg_score());
//
//            List<String> avgStringList = new ArrayList<String>();
//            avgStringList.add("最高分");
//            avgStringList.add("最低分");
//            avgStringList.add("平均分");
//
//            Map<String, Object> retMap = new HashMap<String, Object>();
//            retMap.put("courseName", scoreStats.getCourseName());
//            retMap.put("scoreList", scoreList);
//            retMap.put("avgList", avgStringList);
//            retMap.put("type", "success");
//
//            return retMap;
//        }
//
//        Score score = new Score();
//        score.setCourseId(courseid);
//        List<Score> scoreList = scoreService.getAll(score);
//
//
//        List<Integer> numberList = new ArrayList<Integer>();
//        numberList.add(0);
//        numberList.add(0);
//        numberList.add(0);
//        numberList.add(0);
//        numberList.add(0);
//
//        List<String> rangeStringList = new ArrayList<String>();
//        rangeStringList.add("60分以下");
//        rangeStringList.add("60~70分");
//        rangeStringList.add("70~80分");
//        rangeStringList.add("80~90分");
//        rangeStringList.add("90~100分");
//
//        String courseName = "";
//
//        for(Score sc : scoreList){
//            courseName = sc.getCourseName();  //获取课程名
////            double scoreValue = sc.getScore();//获取成绩
//            if(scoreValue < 60){
//                numberList.set(0, numberList.get(0)+1);
//                continue;
//            }
//            if(scoreValue <= 70 && scoreValue >= 60){
//                numberList.set(1, numberList.get(1)+1);
//                continue;
//            }
//            if(scoreValue <= 80 && scoreValue > 70){
//                numberList.set(2, numberList.get(2)+1);
//                continue;
//            }
//            if(scoreValue <= 90 && scoreValue > 80){
//                numberList.set(3, numberList.get(3)+1);
//                continue;
//            }
//            if(scoreValue <= 100 && scoreValue > 90){
//                numberList.set(4, numberList.get(4)+1);
//                continue;
//            }
//        }
//        Map<String, Object> retMap = new HashMap<String, Object>();
//        retMap.put("courseName", courseName);
//        retMap.put("numberList", numberList);
//        retMap.put("rangeList", rangeStringList);
//        retMap.put("type", "success");
//        return retMap;
//    }

}
