package com.fans.bravegirls.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import com.fans.bravegirls.vo.code.OneSignalSegment;
import com.fans.bravegirls.vo.model.PushHistoryVo;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OneSignalMessageService {

    private final String onesignal_url = "https://onesignal.com/api/v1/notifications";
    private final String aos_rest_api_key = "OTMxYWNiNmYtMWEwZi00MTg2LTg0NTItNmZlMjQwNjJkNjAx";
    private final String aos_app_id = "04dacac2-b557-46bb-8845-c8d5468268d3";


    private final String ios_rest_api_key = "ZTdjYzc1NjktYzUwOC00Nzk1LWIzNjEtOWIyMGEyMmE3MGM0";
    private final String ios_app_id = "8eadf95f-a1ad-47db-97f3-a9aa43c1fc50";

    @Autowired
    private PushHistoryService pushHistoryService;

    //메시지 보내기
    @Async
    public void send_message(HashMap<String, Object> data_param, String message,
            HashMap<String, Object> main_param, OneSignalSegment segment) {

        String jsonResponse = "";

        try {
        	
        	Date today = new Date(); 
        	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        	String createdDate = format1.format(today);
        	
        	PushHistoryVo pushHistoryVo = new PushHistoryVo();
        	pushHistoryVo.setSegment(segment.toString());
        	pushHistoryVo.setMessage(message);
        	pushHistoryVo.setUrl((String)main_param.get("url"));
        	pushHistoryVo.setCreatedDate(createdDate);
        	
        	pushHistoryService.insertPushHistory(pushHistoryVo);
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        try {
        	//aos 보내기
        	send_message(data_param , message, main_param , segment , aos_rest_api_key , aos_app_id);

            
        	//ios 보내기
        	//send_message(data_param , message, main_param , segment , ios_rest_api_key , ios_app_id);

        } catch(Throwable t) {
            t.printStackTrace();
        }

        System.out.println("jsonResponse:\n" + jsonResponse);
    }
    
    
    public void send_message_aos(HashMap<String, Object> data_param, String message,
            HashMap<String, Object> main_param, OneSignalSegment segment ) {
    	
    	String jsonResponse = "";

        try {
        	//aos 보내기
        	send_message(data_param , message, 
        			main_param , segment , aos_rest_api_key , aos_app_id);


        } catch(Throwable t) {
            t.printStackTrace();
        }

        System.out.println("jsonResponse:\n" + jsonResponse);
    }
    
    public void send_message_ios(HashMap<String, Object> data_param, String message,
            HashMap<String, Object> main_param, OneSignalSegment segment ) {
    	
    	String jsonResponse = "";

        try {
        	//aos 보내기
        	send_message(data_param , message, 
        			main_param , segment , ios_rest_api_key , ios_app_id);


        } catch(Throwable t) {
            t.printStackTrace();
        }

        System.out.println("jsonResponse:\n" + jsonResponse);
    }
    
    public String send_message(HashMap<String, Object> data_param, String message,
            HashMap<String, Object> main_param, OneSignalSegment segment , String rest_api_key , String app_id) {
    	
    	String jsonResponse = "";
    	
    	try {
    		URL url = new URL(onesignal_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic " + rest_api_key); // REST API KEY

            con.setRequestMethod("POST");
			/*
			String strJsonBody = "{"
			+   "\"app_id\": \""+app_id+"\"," // ONESIGNAL APP IDA
			+   "\"included_segments\": [\"Subscribed Users\"],"
			+   "\"data\": {\"foo\": \"bar\"},"
			+   "\"contents\": {\"en\": \""+message+"\"}"
			+ "}";
			*/

            JSONObject contents_obj = new JSONObject();
            contents_obj.put("en", message);

            JSONObject data_obj = new JSONObject();

            data_obj.putAll(data_param);

            JSONObject body_obj = new JSONObject();
            body_obj.put("app_id", app_id);
            body_obj.put("included_segments", segment.getSegmentName());
            body_obj.put("data", data_obj);
            body_obj.put("contents", contents_obj);

            if(main_param != null) {
                body_obj.putAll(main_param);
            }


            System.out.println("strJsonBody:\n" + body_obj.toJSONString());
            //System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = body_obj.toJSONString().getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if(httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }

            
            

        } catch(Throwable t) {
            t.printStackTrace();
        }
    	
    	System.out.println("jsonResponse:\n" + jsonResponse);
    	
    	return jsonResponse;
    }
}



