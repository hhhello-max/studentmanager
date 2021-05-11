package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Candidate;
import com.pjpy.studentManager.util.PageBean;

import java.util.List;
import java.util.Map;

public interface CandidateService {
    PageBean<Candidate> queryPage(Map<String, Object> paramMap);

    boolean isCandidate(String sn);

    double getGrade(String sn);

    int addCandidate(Candidate candidate);

    int editCandidate(Candidate candidate);

    int deleteCandidate(Integer id);

}
