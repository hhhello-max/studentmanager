package com.pjpy.studentManager.controller;

import com.pjpy.studentManager.domain.Score;
import com.pjpy.studentManager.domain.Student;
import com.pjpy.studentManager.service.StudentService;
import com.pjpy.studentManager.util.AjaxResult;
import com.pjpy.studentManager.util.Const;
import com.pjpy.studentManager.util.Data;
import com.pjpy.studentManager.util.PageBean;
import org.apache.poi.ss.usermodel.Cell;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    /**
     * 跳转学生列表页面
     *
     * @return
     */
    @GetMapping("/student_list")
    public String studentList() {
        return "/student/studentList";
    }

    @GetMapping("/information")
    public String studentInfo() {
        return "/student/info";
    }


    /**
     * 异步加载学生列表
     *
     * @param page
     * @param rows
     * @param studentName
     * @param from
     * @param session
     * @return
     */
    @RequestMapping("/getStudentList")
    @ResponseBody
    public Object getStudentList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "rows", defaultValue = "100") Integer rows,
                                 String studentName,String from, HttpSession session) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("pageno", page);
        paramMap.put("pagesize", rows);
        if (!StringUtils.isEmpty(studentName)) paramMap.put("username", studentName);

        //判断是老师还是学生权限
        Student student = (Student) session.getAttribute(Const.STUDENT);
        if (!StringUtils.isEmpty(student)) {
            //是学生权限，只能查询自己的信息
            paramMap.put("sn", student.getSn());
        }

        PageBean<Student> pageBean = studentService.queryPage(paramMap);
        if (!StringUtils.isEmpty(from) && from.equals("combox")) {
            return pageBean.getDatas();
        } else {
            Map<String, Object> result = new HashMap();
            result.put("total", pageBean.getTotalsize());
            result.put("rows", pageBean.getDatas());
            return result;
        }
    }

    /**
     * 删除学生
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteStudent")
    @ResponseBody
    public AjaxResult deleteStudent(Data data) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            List<String> sn = data.getSn();
            int count = studentService.deleteStudent(sn);
            if (count > 0) {
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("全部删除成功");

            } else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("删除失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("删除失败");
        }
        return ajaxResult;
    }


    /**
     * 添加学生
     *
     * @param student
     * @return
     * @throws IOException
     */
    @RequestMapping("/addStudent")
    @ResponseBody
    public AjaxResult addStudent(Student student) {

        AjaxResult ajaxResult = new AjaxResult();
        //保存学生信息到数据库
        try {
            int count = studentService.addStudent(student);
            if (count > 0) {
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("保存成功");
            } else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("保存失败");
        }

        ajaxResult.setSuccess(true);
        return ajaxResult;
    }

    @PostMapping("/getStudent")
    @ResponseBody
    public AjaxResult getStudent(@RequestParam(value = "sn", defaultValue = "0") String sn) {
        AjaxResult ajaxResult = new AjaxResult();
        if (!sn.equals("0")) {
            Student student = studentService.findBySn(sn);
        }
        return ajaxResult;

    }

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    @PostMapping("/editStudent")
    @ResponseBody
    public AjaxResult editStudent(Student student) {
        System.out.println(student.toString());
        AjaxResult ajaxResult = new AjaxResult();
        try {
            int count = studentService.editStudent(student);
            if (count > 0) {
                ajaxResult.setSuccess(true);
                ajaxResult.setMessage("修改成功");
            } else {
                ajaxResult.setSuccess(false);
                ajaxResult.setMessage("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("修改失败");
        }
        return ajaxResult;
    }

    @PostMapping("/importStudent")
    @ResponseBody
    public AjaxResult importStudent(@RequestParam("importStudent")MultipartFile importStudent, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        AjaxResult ajaxResult = new AjaxResult();
        try {
            InputStream inputStream = importStudent.getInputStream();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);

            String sn;
            String username;
            String password;
            String clazz;
            String sex;
            double age;
            String email = null;
            String phone = null;

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
                    errorMsg += "第" + rowNum + "行姓名缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    username = cell.getStringCellValue();
                }

                //第2列
                cell = row.getCell(2);
                if (cell == null) {
                    errorMsg += "第" + rowNum + "行密码缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    password = cell.getStringCellValue();
                }

                //第3列
                cell = row.getCell(3);
                if (cell == null) {
                    errorMsg += "第" + rowNum + "行班级缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    clazz = cell.getStringCellValue();
                }
                //
                cell = row.getCell(4);
                if (cell == null) {
                    errorMsg += "第" + rowNum + "行性别缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    sex = cell.getStringCellValue();
                }
                //
                cell = row.getCell(5);
                if (cell == null) {
                    errorMsg += "第" + rowNum + "行年龄缺失！\n";
                    continue;
                }else {
                    cell.setCellType(cell.CELL_TYPE_NUMERIC);
                    age = cell.getNumericCellValue();
                }

                cell = row.getCell(6);
                if (cell != null){
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    email = cell.getStringCellValue();
                }

                cell = row.getCell(7);
                if (cell != null){
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    phone = cell.getStringCellValue();
                }

                System.out.println(phone);

                Student student = new Student();
                student.setSn(sn);
                student.setUsername(username);
                student.setPassword(password);
                student.setClazz(clazz);
                student.setSex(sex);
                student.setAge((int)age);
                student.setEmail(email);
                student.setMobile(phone);

                int i = studentService.addStudent(student);
                if(i > 0){
                    count ++ ;
                }

            }
            errorMsg += "成功录入" + count + "条学生信息！";
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
     */
    @RequestMapping("/exportStudent")
    @ResponseBody
    private void exportScore(HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("student_list"+".xlsx", "UTF-8"));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");

            ServletOutputStream outputStream = response.getOutputStream();
            List<Student> studentList = studentService.getAll();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet createSheet = xssfWorkbook.createSheet("学生列表");
            XSSFRow createRow = createSheet.createRow(0);
            createRow.createCell(0).setCellValue("序号");
            createRow.createCell(1).setCellValue("学号");
            createRow.createCell(2).setCellValue("姓名");
            createRow.createCell(3).setCellValue("班级");
            createRow.createCell(4).setCellValue("性别");
            createRow.createCell(5).setCellValue("年龄");
            createRow.createCell(6).setCellValue("邮箱");
            createRow.createCell(7).setCellValue("电话");
            //实现将数据装入到excel文件中
            int row = 1;
            for( Student s:studentList){
                createRow = createSheet.createRow(row++);
                createRow.createCell(0).setCellValue(s.getId());
                createRow.createCell(1).setCellValue(s.getSn());
                createRow.createCell(2).setCellValue(s.getUsername());
                createRow.createCell(3).setCellValue(s.getClazz());
                createRow.createCell(4).setCellValue(s.getSex());
                createRow.createCell(5).setCellValue(s.getAge());
                createRow.createCell(6).setCellValue(s.getEmail());
                createRow.createCell(7).setCellValue(s.getMobile());
            }
            xssfWorkbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
