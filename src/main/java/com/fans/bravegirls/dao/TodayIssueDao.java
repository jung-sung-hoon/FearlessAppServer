package com.fans.bravegirls.dao;

import java.util.List;
import com.fans.bravegirls.vo.model.TodayIssueVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TodayIssueDao {
    List<TodayIssueVo> selectTodayIssuesByStartDate(String yyyyMM);
}
