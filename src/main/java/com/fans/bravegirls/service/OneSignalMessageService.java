package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OneSignalMessageService {

	private final String onesignal_url 	= "https://onesignal.com/api/v1/notifications";
	private final String rest_api_key	= "YmQwYzI4NjktZGQ4NC00ZTBhLWFiMjYtNjgwOGNmZGIzOTdj";
	private final String app_id			= "6395ae43-f8e5-4862-b70a-da8402d413c8";

    //메시지 보내기
	@Async
    public void send_message(HashMap<String,Object> data_param , String message) {
		
    	String jsonResponse = "";
    	
    	try
		{
			URL url = new URL(onesignal_url);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
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
			contents_obj.put("en" , message);
			
			JSONObject data_obj = new JSONObject();
			//data_obj.put("foo" , "bar");
			
			data_obj.putAll(data_param);
			
			ArrayList<String> users = new ArrayList<>();
			users.add("Subscribed Users");
			
			JSONObject body_obj = new JSONObject();
			body_obj.put("app_id", app_id);
			body_obj.put("included_segments", users);
			body_obj.put("data", data_obj);
			body_obj.put("contents" , contents_obj);
			
   
			System.out.println("strJsonBody:\n" + body_obj.toJSONString());
			//System.out.println("strJsonBody:\n" + strJsonBody);
			
			byte[] sendBytes = body_obj.toJSONString().getBytes("UTF-8");
			con.setFixedLengthStreamingMode(sendBytes.length);
			
			OutputStream outputStream = con.getOutputStream();
			outputStream.write(sendBytes);
			
			int httpResponse = con.getResponseCode();
			System.out.println("httpResponse: " + httpResponse);

			if (  httpResponse >= HttpURLConnection.HTTP_OK
					&& httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
				Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
			}
			else {
				Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
			}
			
   
		} catch(Throwable t) {
			t.printStackTrace();
		}
    	
    	System.out.println("jsonResponse:\n" + jsonResponse);
    }
}



