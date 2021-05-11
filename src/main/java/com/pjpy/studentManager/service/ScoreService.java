package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Candidate;
import com.pjpy.studentManager.domain.Score;
import com.pjpy.studentManager.util.PageBean;

import java.util.List;
import java.util.Map;


public interface ScoreService {
    PageBean<Score> queryPage(Map<String, Object> paramMap);

    boolean isScore(Score score);

    int addScore(Score score);

    int editScore(Score score);

    int deleteScore(Integer id);

    List<Score> getAll(Score score);

    Candidate getCandidate(double point);

    String getName(String sn);
}
