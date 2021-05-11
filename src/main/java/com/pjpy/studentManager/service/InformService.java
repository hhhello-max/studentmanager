package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Inform;
import com.pjpy.studentManager.util.PageBean;

import java.util.List;
import java.util.Map;

public interface InformService {

    PageBean<Inform> queryPage(Map<String,Object> paramMap);

    List<Inform> getAll();

    int addInform(Inform inform);

    int editInform(Inform inform);

    int deleteInform(Integer id);
}
