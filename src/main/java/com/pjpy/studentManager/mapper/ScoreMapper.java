package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Candidate;
import com.pjpy.studentManager.domain.Score;

import java.util.List;
import java.util.Map;


public interface ScoreMapper {
    List<Score> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    int addScore(Score score);

    Score isScore(Score score);

    int editScore(Score score);

    int deleteScore(Integer id);

    List<Score> getAll(Score score);

    Candidate getCandidate(double point);

    String getName(String sn);

}
