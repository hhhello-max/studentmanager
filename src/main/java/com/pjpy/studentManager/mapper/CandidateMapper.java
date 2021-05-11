package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Candidate;

import java.util.List;
import java.util.Map;

public interface CandidateMapper {
    List<Candidate> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addCandidate(Candidate candidate);

    Candidate isCandidate(String sn);

    double getGrade(String sn);

    int editCandidate(Candidate candidate);

    int deleteCandidate(Integer id);

}
