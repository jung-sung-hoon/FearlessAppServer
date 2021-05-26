package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.dao.TodayIssueDao;
import com.fans.bravegirls.vo.model.TodayIssueVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodayIssueService {
    private final TodayIssueDao todayIssueDao;

    public List<TodayIssueVo> selectTodayIssuesByStartDate(String yyyyMM) {
        return todayIssueDao.selectTodayIssuesByStartDate(yyyyMM);
    }
}
