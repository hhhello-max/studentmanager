package com.pjpy.studentManager.service.Impl;

import com.pjpy.studentManager.domain.Teacher;
import com.pjpy.studentManager.mapper.TeacherMapper;
import com.pjpy.studentManager.service.TeacherService;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public PageBean<Teacher> queryPage(Map<String, Object> paramMap) {
        PageBean<Teacher> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Teacher> datas = teacherMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = teacherMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int deleteTeacher(List<String> sn) {
        return teacherMapper.deleteTeacher(sn);
    }

    @Override
    public int addTeacher(Teacher teacher) {
        return teacherMapper.addTeacher(teacher);
    }

    @Override
    public Teacher findBySn(String sn) {
        return teacherMapper.findBySn(sn);
    }

    @Override
    public int editTeacher(Teacher teacher) {
        return teacherMapper.editTeacher(teacher);
    }

    @Override
    public Teacher findByTeacher(Teacher teacher) {
        return teacherMapper.findByTeacher(teacher);
    }

    @Override
    public int editPswdByTeacher(Teacher teacher) {
        return teacherMapper.editPswdByTeacher(teacher);
    }
}
