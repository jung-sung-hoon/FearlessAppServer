package com.fans.bravegirls.batch;

import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.common.utils.TelegramMessage;
import com.fans.bravegirls.service.EventsService;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.EventsVo;


@RequiredArgsConstructor
@Component
@Transactional
public class EventBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    
    @Autowired 
    OneSignalMessageService oneSignalMessageService;
    
    @Autowired
    EventsService eventsService;

    /**
     * 이벤트 D-1 , 오전 10시에 알람 보낸다.
     */
    //@Scheduled(cron = "00 00 10 * * *")
    public void selectEventDeadline() {

        L.info("[이벤트 D-1 체크 시작]");
        
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
				
				oneSignalMessageService.send_message(data_param , title);
        		
        	}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        
        L.info("[이벤트 D-1 체크 종료]");
    }
    
    
    /**
     * 00 시에 이벤트 지간것들 is_in_progress false 로 변경
     */
    @Scheduled(cron = "00 00 00 * * *")
    public void updateEventEnd() {

        L.info("[이벤트 false 처리 시작]");
        
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
        
    	L.info("[이벤트 false 처리 종료]");
    }

}
