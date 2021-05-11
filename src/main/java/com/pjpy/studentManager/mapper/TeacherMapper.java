package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Teacher;

import java.util.List;
import java.util.Map;


public interface TeacherMapper {
    List<Teacher> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int deleteTeacher(List<String> sn);

    int addTeacher(Teacher teacher);

    Teacher findBySn(String sn);

    int editTeacher(Teacher teacher);

    Teacher findByTeacher(Teacher teacher);

    int editPswdByTeacher(Teacher teacher);
}
