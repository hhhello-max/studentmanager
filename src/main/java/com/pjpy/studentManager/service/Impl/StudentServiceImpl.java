package com.pjpy.studentManager.service.Impl;

import com.pjpy.studentManager.domain.Student;
import com.pjpy.studentManager.mapper.StudentMapper;
import com.pjpy.studentManager.service.StudentService;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageBean<Student> queryPage(Map<String, Object> paramMap) {
        PageBean<Student> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Student> datas = studentMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = studentMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public List<Student> getAll() {
        return studentMapper.getAll();
    }

    @Override
    public int deleteStudent(List<String> sn) {
        return studentMapper.deleteStudent(sn);
    }

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public Student findBySn(String sn) {
        return studentMapper.findBySn(sn);
    }

    @Override
    public int editStudent(Student student) {
        return studentMapper.editStudent(student);
    }

    @Override
    public Student findByStudent(Student student) {
        return studentMapper.findByStudent(student);
    }

    @Override
    public boolean isStudentByClazzId(Integer id) {
        List<Student> studentList = studentMapper.isStudentByClazzId(id);
        if (studentList.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getClazz(String sn) {
        return studentMapper.getClazz(sn);
    }

    @Override
    public int editPswdByStudent(Student student) {
        return studentMapper.editPswdByStudent(student);
    }

    @Override
    public int findByName(String name) {
        return studentMapper.findByName(name);
    }
}
