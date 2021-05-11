package com.pjpy.studentManager.service.Impl;

import com.pjpy.studentManager.domain.Application;
import com.pjpy.studentManager.mapper.ApplicationMapper;
import com.pjpy.studentManager.service.ApplicationService;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public PageBean<Application> queryPage(Map<String, Object> paramMap) {
        PageBean<Application> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Application> datas = applicationMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = applicationMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public PageBean<Application> queryBySn(Map<String, Object> paramMap, String sn) {
        PageBean<Application> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Application> datas = applicationMapper.queryBySn(paramMap,sn);
        pageBean.setDatas(datas);

        Integer totalsize = applicationMapper.queryCountBySn(sn);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }


    @Override
    public int addApplication(Application application) {
        return applicationMapper.addApplication(application);
    }

    @Override
    public int editApplication(Application application) {
        return applicationMapper.editApplication(application);
    }

    @Override
    public int checkApplication(Application application) {
        return applicationMapper.checkApplication(application);
    }

    @Override
    public int deleteApplication(Integer id) {
        return applicationMapper.deleteApplication(id);
    }
}
