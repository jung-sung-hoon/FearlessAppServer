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
    
    
    //이벤트 D-1 , 오전 10시에 알람 보낸다.
    List<EventsVo> selectEventDeadline(String endTime);
    
    
    //00 시에 이벤트 지간것들 is_in_progress false 로 변경
    int updateEventEnd(String endTime);
}
