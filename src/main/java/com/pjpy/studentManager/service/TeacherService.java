package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Teacher;
import com.pjpy.studentManager.util.PageBean;

import java.util.List;
import java.util.Map;


public interface TeacherService {
    PageBean<Teacher> queryPage(Map<String, Object> paramMap);

    int deleteTeacher(List<String> sn);

    int addTeacher(Teacher teacher);

    Teacher findBySn(String sn);

    int editTeacher(Teacher teacher);

    Teacher findByTeacher(Teacher teacher);

    int editPswdByTeacher(Teacher teacher);
}
