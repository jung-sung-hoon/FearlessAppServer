package com.fans.bravegirls.batch.biz.component;


import lombok.extern.slf4j.Slf4j;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fans.bravegirls.batch.biz.service.ScheduledService;
import com.fans.bravegirls.batch.biz.utils.HTTPUtil;
import com.fans.bravegirls.batch.biz.vo.model.ScheduleVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


@Slf4j
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStatsBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired
    ScheduledService scheduledService;
    
    
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
			
			DateFormat out_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
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
    	scheduledService.call_scheduled();
    }
    
    

   
    //@Test
    public void temp_select() {
    	
    	String regYyyymm = "202103";
    	
    	List<ScheduleVo> result_list = scheduledService.selectScheduled(regYyyymm);
    	
    	for(ScheduleVo one_obj : result_list) {
    		System.out.println(one_obj);
    	}
    }

}
