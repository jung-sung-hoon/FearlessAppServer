package com.fans.bravegirls.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelegramMessage {

	
	private static String Token_local = "1826419665:AAE0yGYwOleDjjlqVsUDbXQpjXPgI3zJhX8";
	private static String chat_id_local = "627359686";
	
	private static String Token_real = "1826437448:AAGdDTiTnn8RRM7lN9VVZBmVjfcMZo3ItYc";
	private static String chat_id_real = "@fearless_siren";

	
	public static void funcTelegram(String message){
		
		BufferedReader in = null;
		
		 try {
			 URL obj = new URL("https://api.telegram.org/bot" + Token_local + "/sendmessage?chat_id=" + chat_id_local + "&text=" + message); // 호출할 url
			 
			 HttpURLConnection con = (HttpURLConnection)obj.openConnection();
			 con.setRequestMethod("GET");
			 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			 String line;
			 
			 while((line = in.readLine()) != null) { // response를 차례대로 출력
				 System.out.println(line);
			 }		 
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 if(in != null) try { in.close(); } catch(Exception e) { e.printStackTrace(); }
		 }
		 
	}
	
	public static void funcTelegram_real(String message){
		
		BufferedReader in = null;
		
		 try {
			 URL obj = new URL("https://api.telegram.org/bot" + Token_real + "/sendmessage?chat_id=" + chat_id_real + "&text=" + message); // 호출할 url
			 
			 HttpURLConnection con = (HttpURLConnection)obj.openConnection();
			 con.setRequestMethod("GET");
			 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			 String line;
			 
			 while((line = in.readLine()) != null) { // response를 차례대로 출력
				 System.out.println(line);
			 }		 
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 if(in != null) try { in.close(); } catch(Exception e) { e.printStackTrace(); }
		 }
		 
		 /*
		 in = null;
			
		 try {
			 URL obj = new URL("https://api.telegram.org/bot" + Token + "/sendmessage?chat_id=" + chat_id + "&text=" + message); // 호출할 url
			 
			 HttpURLConnection con = (HttpURLConnection)obj.openConnection();
			 con.setRequestMethod("GET");
			 in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			 String line;
			 
			 while((line = in.readLine()) != null) { // response를 차례대로 출력
				 System.out.println(line);
			 }		 
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 if(in != null) try { in.close(); } catch(Exception e) { e.printStackTrace(); }
		 }
		 */
		 
	}
	
	public static void main(String[] args) {
		String message = "테스트 메시지";
		funcTelegram(message);
		
		
	}
	
	
}
