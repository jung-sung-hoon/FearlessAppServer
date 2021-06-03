package com.fans.bravegirls.biz.component;


import com.fans.bravegirls.vo.code.EventCategory;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.common.utils.TelegramMessage;
import com.fans.bravegirls.service.EventsService;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.EventsVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEvent {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    @Autowired 
    OneSignalMessageService oneSignalMessageService;
    
    @Autowired
    EventsService eventsService;
    
    //@Test
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
    		List<EventsVo> result_list = eventsService.selectEventsInProgress(EventCategory.event);
        	
        	for(EventsVo one_obj : result_list) {
        		System.out.println(one_obj);
        	}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    //이벤트 D-1 , 오전 10시에 알람 보낸다.
    //@Test
    public void selectEventDeadline() {
    	
    	String endTime = "";
    	
    	try {
    		
    		String snsKind = SnsKind.event_dead_line.toString();
    		
    		HashMap<String,Object> data_param = new HashMap<>();
    		data_param.put(DataType.notiType.toString(), snsKind);
    		
    		SimpleDateFormat start_format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    		
    		Date start_date = new Date();
            
            Calendar c = Calendar.getInstance();
            c.setTime(start_date);
            c.add(Calendar.DATE, 1);
            start_date = c.getTime();
    		
    		endTime = start_format.format(start_date);
    		
    		List<EventsVo> result_list = eventsService.selectEventDeadline(endTime);
        	
        	for(EventsVo one_obj : result_list) {
        		System.out.println(one_obj);
        		
        		String end_time = one_obj.getEndTime();
        		String title 	= "'"+one_obj.getTitle()+"' 의 이벤트 종료일이 1일 남았습니다. [종료시간 : " + end_time + "]";
        		
        		TelegramMessage.funcTelegram(title);
				
				oneSignalMessageService.send_message(data_param , title , null);
        		
        	}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    //@Test
    public void updateEventEnd() {
    	
    	String endTime = "";
    	
    	try {
    		
    		SimpleDateFormat start_format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    		
    		Date start_date = new Date();
    		
    		endTime = start_format.format(start_date);
    		
    		System.out.println("endTime = " + endTime);
    		
    		int result = eventsService.updateEventEnd(endTime);
        	
        	System.out.println(result);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    //이벤트 시작일 알림
    //@Test
    public void selectEventStart() {
    	
    	String startTime 	= "";
    	String endTime 		= "";
    	
    	try {
    		//이벤트 시작
    		String snsKind = SnsKind.event_start.toString();
    		
    		HashMap<String,Object> data_param = new HashMap<>();
    		data_param.put(DataType.notiType.toString(), snsKind);
    		
    		Date now_date = new Date();
    		
    		SimpleDateFormat start_format = new SimpleDateFormat("yyyy-MM-dd");
    		
    		startTime 	= start_format.format(now_date) + " 00:00:00";
    		endTime 	= start_format.format(now_date) + " 23:59:59";
    		
    		System.out.println("startTime = " + startTime);
    		System.out.println("endTime   = " + endTime);
    		
    		EventsVo eventsVo = new EventsVo();
    		eventsVo.setStartTime(startTime);
    		eventsVo.setEndTime(endTime);
    		
    		List<EventsVo> result_list = eventsService.selectEventStart(eventsVo);
        	
        	System.out.println(result_list);
        	
        	for(EventsVo one_obj : result_list) {
        		System.out.println(one_obj);
        		
        		String start_time = one_obj.getStartTime();
        		String end_time = one_obj.getEndTime();
        		String title 	= "'"+one_obj.getTitle()+"' 의 이벤트가 시작되었습니다. [시작 시간 : " + start_time + " , 종료 시간 : " + end_time + "]";
        		
        		TelegramMessage.funcTelegram(title);
				
				oneSignalMessageService.send_message(data_param , title , null);
        		
        	}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }

}
