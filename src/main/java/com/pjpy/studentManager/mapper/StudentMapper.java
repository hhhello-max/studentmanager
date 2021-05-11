package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Student;

import java.util.List;
import java.util.Map;


public interface StudentMapper {
    List<Student> queryList(Map<String, Object> paramMap);

    List<Student> getAll();

    Integer queryCount(Map<String, Object> paramMap);

    int deleteStudent(List<String> sn);

    int addStudent(Student student);

    Student findBySn(String sn);

    int editStudent(Student student);

    Student findByStudent(Student student);

    List<Student> isStudentByClazzId(Integer id);

    String getClazz(String sn);

    int editPswdByStudent(Student student);

    int findByName(String name);
}
