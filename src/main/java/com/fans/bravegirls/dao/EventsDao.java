package com.fans.bravegirls.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.vo.model.EventsVo;

import java.util.List;

@Mapper
@Repository
public interface EventsDao {
    

    //이벤트 조회
    List<EventsVo> selectEventsInProgress();
}
