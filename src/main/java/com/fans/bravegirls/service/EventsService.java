package com.fans.bravegirls.service;

import com.fans.bravegirls.vo.model.EventsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.EventsDao;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventsService {

    private final EventsDao eventsDao;


    //이벤트 조회
    public List<EventsVo> selectEventsInProgress() {

        return eventsDao.selectEventsInProgress();
    }
    
    
    //이벤트 D-1 , 오전 10시에 알람 보낸다.
    public List<EventsVo> selectEventDeadline(String endTime) {
        return eventsDao.selectEventDeadline(endTime);
    }
    
    
    //00 시에 이벤트 지간것들 is_in_progress false 로 변경
    public int updateEventEnd(String endTime) {
    	return eventsDao.updateEventEnd(endTime);
    }
}



