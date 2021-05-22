package com.fans.bravegirls.biz.component;


import lombok.extern.slf4j.Slf4j;

import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;



@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestOneSignalMessage {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    //@Test
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
    
}
