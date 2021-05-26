package com.fans.bravegirls.biz.component;


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

import com.fans.bravegirls.common.utils.HTTPUtil;
import com.fans.bravegirls.common.utils.TelegramMessage;
import com.fans.bravegirls.dao.InstagramDao;
import com.fans.bravegirls.service.InstagramService;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.MediaKind;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.CookieInfoVo;
import com.fans.bravegirls.vo.model.SnsUserInfoVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTwitterApiBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    @Autowired 
    OneSignalMessageService oneSignalMessageService;
    
    @Autowired
    InstagramService instagramService;
    
    
    //트위터 등록하기
    public void call_twitter_tw(SnsUserInfoVo one_vo , HashMap<String,String> headerData , HashMap<String,Object> data_param ) {
    	
    	String id = one_vo.getId();
    	long last_date = Long.parseLong(one_vo.getLastUpdateTime());
    	
    	String url = "https://api.twitter.com/2/users/"+id+"/tweets?tweet.fields=created_at";
    	
    	HTTPUtil httpManager = new HTTPUtil(url);
		httpManager.setHeader(headerData);
		
		
		String result = httpManager.httpGetSend(null);
		
		if(result == null || result.length() == 0) {
			return;
		}
		
		JSONParser parsor = new JSONParser();
		try {
			
			JSONObject order_json = (JSONObject)parsor.parse(result);
			
			JSONArray datas = (JSONArray)order_json.get("data");
			
			int size = datas.size();
			
			if(size > 0) {
				
				JSONObject one_data = (JSONObject)datas.get(0);
				
				String created_at = (String)one_data.get("created_at");
		    	
		    	
	    		TimeZone utcZone = TimeZone.getTimeZone("UTC");
		    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		    	simpleDateFormat.setTimeZone(utcZone);
		    	
		    	Date date = simpleDateFormat.parse(created_at);
		    	
		    	long created_at_l = date.getTime() / 1000;
		    	
		    	if(last_date < created_at_l) {
					System.out.println("신규 트윗 등록됨");
					
					one_vo.setLastUpdateTime(String.valueOf(created_at_l));
					
					//db 에 마지막 날짜 수정
					int update_cnt = instagramService.updateSnsUserInfoLastUpdateTime(one_vo);
					
					
					//푸쉬 보내기
					if(update_cnt > 0) {
						System.out.println("push 보내기");
						
						String message = one_vo.getUserId() + " 님의 트윗이 등록 되었습니다.";
						
						TelegramMessage.funcTelegram(message);
						
						oneSignalMessageService.send_message(data_param , message);
					}
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    
    //@Test
    public void twitter_tw() {
    	
    	String snsKind = SnsKind.twitter.toString();
    	String mediaKind = MediaKind.tw.toString();
    	
    	//쿠키 데이타 저장
    	HashMap<String,String> headerData = new HashMap<>();
		set_cookie_info(headerData , snsKind , "0");
		
		//트위터 트윗  시작
		SnsUserInfoVo snsUserInfoVo = new SnsUserInfoVo();
		snsUserInfoVo.setSnsKind(snsKind);
		snsUserInfoVo.setMediaKind(mediaKind);
		
		//원시그널 타입
		HashMap<String,Object> data_param = new HashMap<>();
    	data_param.put(DataType.notiType.toString(), snsKind);
		
		List<SnsUserInfoVo> list = instagramService.selectSnsUserInfo(snsUserInfoVo);
    	
    	for(SnsUserInfoVo one_vo : list) {
    		
        	//트윗 조회
    		call_twitter_tw( one_vo , headerData , data_param);
        	
    	}
    }
    
    
    public void set_cookie_info(HashMap<String,String> headerData , String kind, String seq) {
    	
    	CookieInfoVo cookieInfoVo = new CookieInfoVo();
    	cookieInfoVo.setSeq(seq);
    	cookieInfoVo.setSnsKind(kind);
    	
    	List<CookieInfoVo> list = instagramService.selectCookieInfo(cookieInfoVo);
    	
    	for(CookieInfoVo one_cookie : list) {
    		
    		String cookie_id = one_cookie.getCookieId();
    		String cookie_val = one_cookie.getCookieVal();
    		
    		headerData.put(cookie_id, cookie_val);
    		
    	}
    }
    
    
    //@Test
    public void test() {
    	
    	String created_at = "2021-05-15T05:36:13.000Z";
    	
    	try {
    		TimeZone utcZone = TimeZone.getTimeZone("UTC");
	    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    	simpleDateFormat.setTimeZone(utcZone);
	    	
	    	Date date = simpleDateFormat.parse(created_at);
	    	
	    	System.out.println(date);
	    	System.out.println(date.getTime());
	    	
	    	long created_at_l = date.getTime() / 1000;
	    	
	    	System.out.println(created_at_l);
	    	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    

}
