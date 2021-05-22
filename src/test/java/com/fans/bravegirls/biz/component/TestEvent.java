package com.fans.bravegirls.biz.component;


import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.service.EventsService;
import com.fans.bravegirls.vo.model.EventsVo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEvent {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired
    EventsService eventsService;
    
    @Test
    public void selectEvents() {
    	
    	
    	String regYyyymm = "";
    	
    	//regYyyymm = regYyyymm + "01000000";
    	try {
    		//Date start_date = null;
    		//DateFormat start_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		/*
    		DateFormat start_format = new SimpleDateFormat("yyyyMMddHHmmss");
    		start_date = start_format.parse(regYyyymm);
    		
    		DateFormat chg_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		regYyyymm = chg_format.format(start_date);
    		
    		System.out.println(regYyyymm);
    		*/
    		List<EventsVo> result_list = eventsService.selectEvents(regYyyymm);
        	
        	for(EventsVo one_obj : result_list) {
        		System.out.println(one_obj);
        	}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    	
    	
    }

}
