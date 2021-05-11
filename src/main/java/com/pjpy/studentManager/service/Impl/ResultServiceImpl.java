package com.pjpy.studentManager.service.Impl;

import com.pjpy.studentManager.domain.Application;
import com.pjpy.studentManager.domain.Result;
import com.pjpy.studentManager.mapper.ResultMapper;
import com.pjpy.studentManager.service.ResultService;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultMapper resultMapper;

    @Override
    public PageBean<Result> queryPage(Map<String, Object> paramMap) {
        PageBean<Result> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Result> datas = resultMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = resultMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int addResult(Result result) {
        return resultMapper.addResult(result);
    }

    @Override
    public int deleteResult(Integer id) {
        return resultMapper.deleteResult(id);
    }
}
