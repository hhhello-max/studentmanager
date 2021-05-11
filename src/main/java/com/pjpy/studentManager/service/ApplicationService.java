package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Application;
import com.pjpy.studentManager.util.PageBean;

import java.util.Map;

public interface ApplicationService {

    PageBean<Application> queryPage(Map<String, Object> paramMap);

    PageBean<Application> queryBySn(Map<String, Object> paramMap,String sn);

    int addApplication(Application application);

    int editApplication(Application application);

    int checkApplication(Application application);

    int deleteApplication(Integer id);
}
