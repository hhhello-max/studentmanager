package com.pjpy.studentManager.service.Impl;

import com.pjpy.studentManager.domain.Inform;
import com.pjpy.studentManager.mapper.InformMapper;
import com.pjpy.studentManager.service.InformService;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InformServiceImpl implements InformService {

    @Autowired
    private InformMapper informMapper;

    @Override
    public PageBean<Inform> queryPage(Map<String, Object> paramMap) {
        PageBean<Inform> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Inform> datas = informMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = informMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public List<Inform> getAll() {
        return informMapper.getAll();
    }

    @Override
    public int addInform(Inform inform) {
        return informMapper.addInform(inform);
    }

    @Override
    public int editInform(Inform inform) {
        return informMapper.editInform(inform);
    }

    @Override
    public int deleteInform(Integer id) {
        return informMapper.deleteInform(id);
    }
}
