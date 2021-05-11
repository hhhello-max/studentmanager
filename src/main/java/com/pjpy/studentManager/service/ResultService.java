package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Result;
import com.pjpy.studentManager.util.PageBean;

import java.util.Map;

public interface ResultService {

    PageBean<Result> queryPage(Map<String, Object> paramMap);

    int addResult(Result result);

    int deleteResult(Integer id);

}
