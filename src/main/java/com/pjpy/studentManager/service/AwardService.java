package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Award;
import com.pjpy.studentManager.util.PageBean;

import java.util.Map;

public interface AwardService {
    PageBean<Award> queryPage(Map<String, Object> paramMap);

    int addAward(Award award);

    int editAward(Award award);

    int deleteAward(Integer id);
}
