package com.pjpy.studentManager.service.Impl;

import com.pjpy.studentManager.domain.Award;
import com.pjpy.studentManager.mapper.AwardMapper;
import com.pjpy.studentManager.service.AwardService;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardMapper awardMapper;

    @Override
    public PageBean<Award> queryPage(Map<String, Object> paramMap) {
        PageBean<Award> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));
        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Award> datas = awardMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = awardMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public int addAward(Award award) {
        return awardMapper.addAward(award);
    }

    @Override
    public int editAward(Award award) {
        return awardMapper.editAward(award);
    }

    @Override
    public int deleteAward(Integer id) {
        return awardMapper.deleteAward(id);
    }
}
