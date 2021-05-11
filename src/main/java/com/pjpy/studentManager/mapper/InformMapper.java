package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Inform;

import java.util.List;
import java.util.Map;

public interface InformMapper {

    List<Inform> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    List<Inform> getAll();

    int addInform(Inform inform);

    int editInform(Inform inform);

    int deleteInform(Integer id);

}
