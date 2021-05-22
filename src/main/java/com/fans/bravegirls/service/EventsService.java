package com.fans.bravegirls.service;

import com.fans.bravegirls.vo.model.EventsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.EventsDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}



