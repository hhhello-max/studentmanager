package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Award;

import java.util.List;
import java.util.Map;

public interface AwardMapper {

    List<Award> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addAward(Award award);

    int editAward(Award award);

    int deleteAward(Integer id);


}
