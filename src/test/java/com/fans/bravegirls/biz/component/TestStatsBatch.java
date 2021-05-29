package com.fans.bravegirls.biz.component;


import com.fans.bravegirls.vo.model.ScheduleVo;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.service.ScheduleService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Slf4j
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStatsBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired
	ScheduleService scheduleService;
    
    
    /**
     * 타임존에 따른 시간 변환
     * @param localTime
     * @param zone
     * @return
     */
    public String timezone_change(String localTime , String zone) {
    	
    	String outSLocalTime = localTime;
    	
    	try {
	    	DateFormat in_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			
			Date date = in_format.parse(localTime);
			//System.out.println(date);
	
			
			TimeZone tz = TimeZone.getTimeZone(zone);
			
			DateFormat out_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			out_format.setTimeZone(tz);
			
			outSLocalTime = out_format.format(date);
			
			
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
		//System.out.println(outSLocalTime);
    	
    	return outSLocalTime;
    }
    
    //@Test
    public void date_call() {
    	scheduleService.call_scheduled();
    }
    
    

   
    //@Test
    public void temp_select() {
    	
    	String regYyyymm = "202103";
    	
    	List<ScheduleVo> result_list = scheduleService.selectScheduled(regYyyymm);
    	
    	for(ScheduleVo one_obj : result_list) {
    		System.out.println(one_obj);
    	}
    }

}
