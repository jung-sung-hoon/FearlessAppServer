package com.fans.bravegirls.biz.component;


import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpHost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.common.utils.HTTPUtil;
import com.fans.bravegirls.service.InstagramService;
import com.fans.bravegirls.vo.code.MediaKind;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.CookieInfoVo;
import com.fans.bravegirls.vo.model.ProxyServerVo;
import com.fans.bravegirls.vo.model.SnsUserInfoVo;

import java.util.HashMap;
import java.util.List;


@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInstagramScrapBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired
    InstagramService instagramService;
    
    
    
    
    //@Test
    public void get_cookie_data() {
    	
    	String kind = SnsKind.instagram.toString();
    	
    	CookieInfoVo cookieInfoVo = new CookieInfoVo();
    	cookieInfoVo.setSeq("0");
    	cookieInfoVo.setSnsKind(kind);
    	
    	List<CookieInfoVo> list = instagramService.selectCookieInfo(cookieInfoVo);
    	
    	System.out.println(list);
    	
    }
    
    public void set_cookie_info(HashMap<String,String> headerData , String kind , String seq) {
    	
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
    
    public String call_instagram_photo(SnsUserInfoVo one_vo , HashMap<String,String> headerData) {
    	
    	String usr_id = one_vo.getUserId();
    	long last_date = Long.parseLong(one_vo.getLastUpdateTime());
    	
    	String url = "https://www.instagram.com/"+usr_id+"/";
    	
		HTTPUtil httpManager = new HTTPUtil(url);
		httpManager.setHeader(headerData);
					
		String result = httpManager.httpGetSend(null);
		
		result = result.substring(result.indexOf(">window._sharedData"));
		
		result = result.substring(0, result.indexOf(";</script>"));
		
		result = result.substring(result.indexOf("=")+1);
		
		System.out.println(result);
		
		String return_usr_id = "";
		
		JSONParser parsor = new JSONParser();
		try {
			JSONObject order_json = (JSONObject)parsor.parse(result);
			
			System.out.println(order_json);
			
			JSONObject entry_data = (JSONObject)order_json.get("entry_data");
			
			JSONArray ProfilePage = (JSONArray)entry_data.get("ProfilePage");
			
			JSONObject one_profile = (JSONObject)ProfilePage.get(0);
			
			JSONObject graphql = (JSONObject)one_profile.get("graphql");
			
			JSONObject user = (JSONObject)graphql.get("user");
			
			return_usr_id = (String)user.get("id");
			
			System.out.println("usr_id = " + usr_id);
			
			JSONObject edge_owner_to_timeline_media = (JSONObject)user.get("edge_owner_to_timeline_media");
			
			JSONArray edges = (JSONArray)edge_owner_to_timeline_media.get("edges");
			
			System.out.println(edges.size());
			
			int size = edges.size();
			
			if(size > 0) {
				 
				JSONObject one_edges = (JSONObject)edges.get(0);
				
				JSONObject node = (JSONObject)one_edges.get("node");
				
				long taken_at_timestamp = (Long)node.get("taken_at_timestamp");
				System.out.println(taken_at_timestamp);
				
				if(last_date < taken_at_timestamp) {
					System.out.println("신규 사진 등록됨");
					
					one_vo.setLastUpdateTime(String.valueOf(taken_at_timestamp));
					
					//db 에 마지막 날짜 수정
					int update_cnt = instagramService.updateSnsUserInfoLastUpdateTime(one_vo);
					
					//푸쉬 보내기
					if(update_cnt > 0) {
						System.out.println("push 보내기");
					}
					
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("return_usr_id = " + return_usr_id);
    	
    	return return_usr_id;
    }
    
    
    //@Test
    public void instagram_scrap_photo() {
    	String snsKind = SnsKind.instagram.toString();
    	String mediaKind = MediaKind.photo.toString();
    	
    	//쿠키 데이타 저장
    	HashMap<String,String> headerData = new HashMap<>();
		set_cookie_info(headerData , snsKind,"0");
		
		//인스타 그램 사진 크롤링 시작
		SnsUserInfoVo snsUserInfoVo = new SnsUserInfoVo();
		snsUserInfoVo.setSnsKind(snsKind);
		snsUserInfoVo.setMediaKind(mediaKind);
		    	
    	List<SnsUserInfoVo> list = instagramService.selectSnsUserInfo(snsUserInfoVo);
    	
    	for(SnsUserInfoVo one_vo : list) {
    		
    		//String usr_id = "u.nalee";
        	//long last_date = 1619935714;
    		
        	//포토 조회
        	String return_usr_id = call_instagram_photo( one_vo , headerData);
        	
        	//업데이트 한다.
        	one_vo.setId(return_usr_id);
        	
        	instagramService.updateSnsUserInfoId(one_vo);
    	}
    }
    
    
    public void call_instagram_video(SnsUserInfoVo one_vo , HashMap<String,String> headerData ) {
    	
    	String id = one_vo.getId();
    	long last_date = Long.parseLong(one_vo.getLastUpdateTime());
    	
    	String url = "https://i.instagram.com/api/v1/feed/reels_media/?reel_ids="+id;
    	
    	HTTPUtil httpManager = new HTTPUtil(url);
		httpManager.setHeader(headerData);
		
		String result = httpManager.httpGetSend(null);
		
		//System.out.println(result);
		
		JSONParser parsor = new JSONParser();
		try {
			JSONObject order_json = (JSONObject)parsor.parse(result);
			
			JSONArray reels_media = (JSONArray)order_json.get("reels_media");
			
			System.out.println(reels_media);
			
			JSONObject media_one = (JSONObject)reels_media.get(0);
			
			JSONArray items = (JSONArray)media_one.get("items");
			
			System.out.println(items);
			
			if(items != null && items.size() > 0) {
				
				int size = items.size();
					
				JSONObject one_item = (JSONObject)items.get(size-1);
				
				//System.out.println(one_item);
				
				
				long device_timestamp = (Long)one_item.get("taken_at");
				
				//System.out.println(device_timestamp);
				
			    //String formatTime = dateFormat.format(device_timestamp);
			    //System.out.println(device_timestamp + " = " + formatTime);
				
				
				if(last_date < device_timestamp) {
					
					System.out.println("신규 스토리 있다.");
					
					//마지막 시간 저장한다.
					one_vo.setLastUpdateTime(String.valueOf(device_timestamp));
					
					//db 에 마지막 날짜 수정
					int update_cnt = instagramService.updateSnsUserInfoLastUpdateTime(one_vo);
					
					//푸쉬 보내기
					if(update_cnt > 0) {
						System.out.println("push 보내기");
					}
					
				}
					
				
			}
			
		} catch (Exception e) {
			
		}
    	
    }
    
    public void call_instagram_video2(HashMap<String, SnsUserInfoVo> user_info , HashMap<String,String> headerData ) {
    	
    	//String id = one_vo.getId();
    	//long last_date = Long.parseLong(one_vo.getLastUpdateTime());
    	
    	String url = "https://i.instagram.com/api/v1/feed/reels_tray/";
    	
    	HTTPUtil httpManager = new HTTPUtil(url);
		httpManager.setHeader(headerData);
		
		String result = httpManager.httpGetSend(null);
		
		//System.out.println(result);
		
		JSONParser parsor = new JSONParser();
		try {
			JSONObject order_json = (JSONObject)parsor.parse(result);
			
			JSONArray tray = (JSONArray)order_json.get("tray");
			
			System.out.println(tray);
			
			if(tray != null && tray.size() > 0) {
				int size = tray.size();
				
				for(int i = 0 ; i < size ; i++) {
					
					JSONObject one_item = (JSONObject)tray.get(i);
					
					String id = String.valueOf((long)one_item.get("id"));
					
					//갤주 정보가 들어 있는지 확인
					System.out.println(user_info.get(id));
					if(user_info.get(id) == null) {
						continue;
					}
					
					SnsUserInfoVo one_info = (SnsUserInfoVo)user_info.get(id);
					
					long last_date = Long.parseLong(one_info.getLastUpdateTime());
					
					
					//있다면 마지막 시간 확인 할것
					long latest_reel_media = (Long)one_item.get("latest_reel_media");
					
					
					if(last_date < latest_reel_media) {
						
						System.out.println("신규 스토리 있다.");
						
						//마지막 시간 저장한다.
						one_info.setLastUpdateTime(String.valueOf(latest_reel_media));
						
						//db 에 마지막 날짜 수정
						int update_cnt = instagramService.updateSnsUserInfoLastUpdateTime(one_info);
						
						//푸쉬 보내기
						if(update_cnt > 0) {
							System.out.println("push 보내기");
						}
					}
					
				}
				
			}
			
		} catch (Exception e) {
			
		}
    	
    }
   
    //@Test
    public void instagram_scrap_video() {
    	
    	String cookie_info = "4";
    	
    	//프로시 정보 조회
    	ProxyServerVo proxyServerVo = new ProxyServerVo();
    	proxyServerVo.setSeq("1");
    	//ProxyServerVo proxy_info = instagramService.selectProxyServer(proxyServerVo);
    	ProxyServerVo proxy_info = null;
    	
    	HttpHost proxy = null;
    	
    	if(proxy_info != null) {
    		proxy = new HttpHost(proxy_info.getServerIp(), Integer.parseInt(proxy_info.getServerPort()));
    	}
    	
    	String snsKind = SnsKind.instagram.toString();
    	String mediaKind = MediaKind.video.toString();
    	
    	//쿠키 데이타 저장
    	HashMap<String,String> headerData = new HashMap<>();
		set_cookie_info(headerData , snsKind , cookie_info );
		
		//인스타 그램 사진 크롤링 시작
		SnsUserInfoVo snsUserInfoVo = new SnsUserInfoVo();
		snsUserInfoVo.setSnsKind(snsKind);
		snsUserInfoVo.setMediaKind(mediaKind);
		  
		HashMap<String, SnsUserInfoVo> user_info = new HashMap<>();
		
    	List<SnsUserInfoVo> list = instagramService.selectSnsUserInfo(snsUserInfoVo);
    	
    	for(SnsUserInfoVo one_vo : list) {
    		
    		user_info.put(one_vo.getId() , one_vo);
        	
    	}
    	
    	System.out.println(user_info);
    	
    	//call_instagram_video2( user_info , headerData);
    	
    	//instagramService.instagram_scrap_video(cookie_info , proxy);
    	
    	instagramService.instagram_scrap_photo( cookie_info ,  proxy);
		
    }
    

}
