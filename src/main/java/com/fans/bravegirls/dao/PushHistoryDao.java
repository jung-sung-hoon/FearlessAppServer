package com.fans.bravegirls.dao;

import java.util.List;

import com.fans.bravegirls.vo.model.PushHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PushHistoryDao {
    List<PushHistoryVo> selectPushHistory();
    
    int insertPushHistory(PushHistoryVo pushHistoryVo);
}
