package com.pjpy.studentManager.service.Impl;

import com.pjpy.studentManager.domain.Candidate;
import com.pjpy.studentManager.mapper.CandidateMapper;
import com.pjpy.studentManager.service.CandidateService;
import com.pjpy.studentManager.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateMapper candidateMapper;


    @Override
    public PageBean<Candidate> queryPage(Map<String, Object> paramMap) {
        PageBean<Candidate> pageBean = new PageBean<>((Integer) paramMap.get("pageno"),(Integer) paramMap.get("pagesize"));

        Integer startIndex = pageBean.getStartIndex();
        paramMap.put("startIndex",startIndex);
        List<Candidate> datas = candidateMapper.queryList(paramMap);
        pageBean.setDatas(datas);

        Integer totalsize = candidateMapper.queryCount(paramMap);
        pageBean.setTotalsize(totalsize);
        return pageBean;
    }

    @Override
    public boolean isCandidate(String sn) {
        Candidate cd = candidateMapper.isCandidate(sn);
        if(cd != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public double getGrade(String sn) {
        return candidateMapper.getGrade(sn);
    }

    @Override
    public int addCandidate(Candidate candidate) {
        return candidateMapper.addCandidate(candidate);
    }

    @Override
    public int editCandidate(Candidate candidate) {
        return candidateMapper.editCandidate(candidate);
    }

    @Override
    public int deleteCandidate(Integer id) {
        return candidateMapper.deleteCandidate(id);
    }
}
