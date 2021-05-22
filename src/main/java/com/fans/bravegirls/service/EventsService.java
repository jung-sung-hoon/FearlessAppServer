package com.fans.bravegirls.service;

import com.fans.bravegirls.vo.model.EventsVo;
import com.fans.bravegirls.vo.model.ScheduleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.EventsDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventsService {

    private final EventsDao eventsDao;

    


    //이벤트 조회
    public List<EventsVo> selectEvents(String startTime) {

        if(startTime == null || startTime.length() == 0) {

            Date now_date = new Date();

            DateFormat reg_format = new SimpleDateFormat("yyyyMM");
            startTime = reg_format.format(now_date);
        }
        
        Date start_date = null;
        
        startTime = startTime + "01000000";
    	try {

    		DateFormat start_format = new SimpleDateFormat("yyyyMMddHHmmss");
    		start_date = start_format.parse(startTime);
    		
    		DateFormat chg_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		startTime = chg_format.format(start_date);
    		
    		System.out.println(startTime);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        

        return eventsDao.selectEvents(startTime);
    }
}



