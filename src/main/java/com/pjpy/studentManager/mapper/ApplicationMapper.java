package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Application;

import java.util.List;
import java.util.Map;

public interface ApplicationMapper {

    List<Application> queryList(Map<String, Object> paramMap);

    List<Application> queryBySn(Map<String, Object> paramMap,String sn);

    Integer queryCount(Map<String, Object> paramMap);

    Integer queryCountBySn(String sn);

    int addApplication(Application application);

    int editApplication(Application application);

    int checkApplication(Application application);

    int deleteApplication(Integer id);
}
