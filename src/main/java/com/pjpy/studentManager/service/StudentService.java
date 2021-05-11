package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Student;
import com.pjpy.studentManager.util.PageBean;

import java.util.List;
import java.util.Map;


public interface StudentService {
    PageBean<Student> queryPage(Map<String, Object> paramMap);

    List<Student> getAll();

    int deleteStudent(List<String> sn);

    int addStudent(Student student);

    Student findBySn(String sn);

    int editStudent(Student student);

    Student findByStudent(Student student);

    boolean isStudentByClazzId(Integer next);

    String getClazz(String sn);

    int editPswdByStudent(Student student);

    int findByName(String username);
}
