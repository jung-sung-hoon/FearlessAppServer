package com.fans.bravegirls.biz.component;


import com.fans.bravegirls.vo.code.OneSignalSegment;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.common.utils.HTTPUtil;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.SnsKind;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Base64;
import java.util.Base64.*;



@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOneSignalMessage {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    OneSignalMessageService oneSignalMessageService;
    
    
    @Test
    public void send_message() {
    	
    	String message = "테스트 메시지 - 인스타만";
    	
    	String snsKind = SnsKind.instagram.toString();
    	
    	HashMap<String,Object> data_param = new HashMap<>();

    	System.out.println("시작");
    	
    	oneSignalMessageService.send_message(data_param , message , null, OneSignalSegment.Instagram);
    	
    	System.out.println("종료");
    }
    
    @Test
    public void sendPush() {
    	
    	try
		{
    		String message = "테스트 메시지";
    		
			String jsonResponse;
			   
			URL url = new URL("https://onesignal.com/api/v1/notifications");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", "Basic YmQwYzI4NjktZGQ4NC00ZTBhLWFiMjYtNjgwOGNmZGIzOTdj"); // REST API KEY
			
			con.setRequestMethod("POST");
			
			String strJsonBody = "{"
			+   "\"app_id\": \"6395ae43-f8e5-4862-b70a-da8402d413c8\"," // ONESIGNAL APP IDA
			+   "\"included_segments\": [\"Subscribed Users\"],"
			+   "\"data\": {\"foo\": \"bar\"},"
			+   "\"contents\": {\"en\": \""+message+"\"}"
			+ "}";
   
   
   
			System.out.println("strJsonBody:\n" + strJsonBody);
			
			byte[] sendBytes = strJsonBody.getBytes("UTF-8");
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
			System.out.println("jsonResponse:\n" + jsonResponse);
   
		} catch(Throwable t) {
			t.printStackTrace();
		}
    }
    
    @Test
    public void oneSignalMessage() {
    	
    	Encoder encoder = Base64.getEncoder();

    	
    	String link = "https://gall.dcinside.com/mgallery/board/view/?id=bravegirls0409&no=486999&search_head=100&page=5";
    	
    	link = URLEncoder.encode(link);
    	
    	System.out.println(link);
    	
    	link = new String(encoder.encode(link.getBytes()));
    	
    	String subject = "[공지] 홍은지 생일 서포트 오픈[TEST]";
    	
    	subject = URLEncoder.encode(subject);
    	
    	System.out.println(subject);
    	
    	subject = new String(encoder.encode(subject.getBytes()));
    	
    	
    	//이미지 url 
    	String img_url = "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/e35/c0.180.1440.1440a/s320x320/188034101_197178312230248_4152800119850839564_n.jpg?tp=1&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=104&_nc_ohc=VHjd_J4RL0oAX-5J_70&edm=ABfd0MgBAAAA&ccb=7-4&oh=337409a9d4cf8ff3a32fc33f1d27bd91&oe=60C77DD8&_nc_sid=7bff83";
    	
    	img_url = URLEncoder.encode(img_url);
    	
    	System.out.println(img_url);
    	
    	img_url = new String(encoder.encode(img_url.getBytes()));
    	
    	
    	//아이콘 url
    	String icon_url = "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/e35/s320x320/29739648_227320564516371_5489093857851211776_n.jpg?tp=1&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=103&_nc_ohc=ey9F3srLPekAX8YFbks&edm=APU89FABAAAA&ccb=7-4&oh=8c517b405b02dcb1ee503096f20abefa&oe=60C6770F&_nc_sid=86f79a";
    	
    	icon_url = URLEncoder.encode(icon_url);
    	
    	System.out.println(icon_url);
    	
    	icon_url = new String(encoder.encode(icon_url.getBytes()));
    	
    	HashMap<String,Object> data_param = new HashMap<>();
    	//data_param.put("notiType", "dc_noti");
    	data_param.put("push_type", SnsKind.normal.toString());
    	data_param.put("subject", subject.replaceAll("=", ""));
    	data_param.put("link", link.replaceAll("=", ""));
    	
    	data_param.put("img_url", img_url.replaceAll("=", ""));
    	data_param.put("icon_url", icon_url.replaceAll("=", ""));
    	
    	
    	System.out.println(data_param);
    	
    	String url = "http://localhost:19876/api/v1/oneSignal/message";
    	
		HTTPUtil httpManager = new HTTPUtil(url);
		
		httpManager.post_type = 1;		//post
		
					
		String result = httpManager.httpPostSend(data_param);
		
    	
    }
    
}
