package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Result;

import java.util.List;
import java.util.Map;

public interface ResultMapper {

    List<Result> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addResult(Result result);

    int deleteResult(Integer id);

}
