package com.fans.bravegirls.service;

import com.fans.bravegirls.vo.code.OneSignalSegment;
import org.apache.http.HttpHost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fans.bravegirls.common.utils.HTTPUtil;
import com.fans.bravegirls.common.utils.TelegramMessage;
import com.fans.bravegirls.dao.InstagramDao;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.MediaKind;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.CookieInfoVo;
import com.fans.bravegirls.vo.model.ProxyServerVo;
import com.fans.bravegirls.vo.model.SnsUserInfoVo;

import java.util.HashMap;
import java.util.List;

@Service
public class InstagramService {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    @Autowired 
    OneSignalMessageService oneSignalMessageService;
    
    @Autowired
    InstagramDao instagramDao;
    
    
    /**
     * sns 정보 가져 오기
     * @param kind
     * @return
     */
    public List<SnsUserInfoVo> selectSnsUserInfo(SnsUserInfoVo snsUserInfoVo) {
    	return instagramDao.selectSnsUserInfo(snsUserInfoVo);
    }
    
    /**
     * 쿠키 정보 조회
     * @param kind
     * @return
     */
    public List<CookieInfoVo> selectCookieInfo(CookieInfoVo cookieInfoVo) {
    	return instagramDao.selectCookieInfo(cookieInfoVo);
    }
    
    /**
     * 미디어의 최종 등록 시간을 업데이트 한다.
     * @param snsUserInfoVo
     * @return
     */
    public int updateSnsUserInfoLastUpdateTime(SnsUserInfoVo snsUserInfoVo) {
    	return instagramDao.updateSnsUserInfoLastUpdateTime(snsUserInfoVo);
    }

    /**
     * 미디어의 사용자 ID 를 업데이트 한다.
     * @param snsUserInfoVo
     * @return
     */
    public int updateSnsUserInfoId(SnsUserInfoVo snsUserInfoVo) {
    	return instagramDao.updateSnsUserInfoId(snsUserInfoVo);
    }
    
    /**
     * 프록시 정보 조회
     * @param proxyServerVo
     * @return
     */
    public ProxyServerVo selectProxyServer(ProxyServerVo proxyServerVo) {
    	return instagramDao.selectProxyServer(proxyServerVo);
    }
    
    
    
    /**
     * 쿠키 데이타 설정
     * @param headerData
     * @param kind
     */
    public void set_cookie_info(HashMap<String,String> headerData , String kind, String seq) {
    	
    	CookieInfoVo cookieInfoVo = new CookieInfoVo();
    	cookieInfoVo.setSeq(seq);
    	cookieInfoVo.setSnsKind(kind);
    	
    	List<CookieInfoVo> list = selectCookieInfo(cookieInfoVo);
    	
    	for(CookieInfoVo one_cookie : list) {
    		
    		String cookie_id = one_cookie.getCookieId();
    		String cookie_val = one_cookie.getCookieVal();
    		
    		headerData.put(cookie_id, cookie_val);
    		
    	}
    }
    
    
    /**
     * 인스타 그램 사진 스크래핑
     */
    public void instagram_scrap_photo(String seq , HttpHost proxy) {
    	String snsKind = SnsKind.instagram.toString();
    	String mediaKind = MediaKind.photo.toString();
    	
    	//쿠키 데이타 저장
    	HashMap<String,String> headerData = new HashMap<>();
		set_cookie_info(headerData , snsKind, seq);
		
		//인스타 그램 사진 크롤링 시작
		SnsUserInfoVo snsUserInfoVo = new SnsUserInfoVo();
		snsUserInfoVo.setSnsKind(snsKind);
		snsUserInfoVo.setMediaKind(mediaKind);
		    	
    	List<SnsUserInfoVo> list = selectSnsUserInfo(snsUserInfoVo);
    	
    	HashMap<String,Object> data_param = new HashMap<>();
    	data_param.put(DataType.notiType.toString(), snsKind);
    	
    	for(SnsUserInfoVo one_vo : list) {
    		
        	//포토 조회
        	String return_usr_id = call_instagram_photo( one_vo , headerData , data_param , proxy);
        	
        	if(return_usr_id.length() > 0) {
        		//업데이트 한다.
            	one_vo.setId(return_usr_id);
            	
            	updateSnsUserInfoId(one_vo);
        	}
        	
        	
        	try {
        		Thread.sleep(5000);
        	} catch (Exception e) {
        		
        	}
    	}
    }
    
    
    /**
     * 인스타 그램 사진 데이타 스크래핑
     * @param one_vo
     * @param headerData
     * @return
     */
    public String call_instagram_photo(SnsUserInfoVo one_vo , HashMap<String,String> headerData , HashMap<String,Object> data_param , HttpHost proxy) {
    	
    	String usr_id = one_vo.getUserId();
    	long last_date = Long.parseLong(one_vo.getLastUpdateTime());
    	
    	String url = "https://www.instagram.com/"+usr_id+"/";
    	
		HTTPUtil httpManager = new HTTPUtil(url);
		httpManager.setHeader(headerData);
		
		if(proxy != null) {
			httpManager.setProxy(proxy);
		}
		
		String return_usr_id = "";
		
		String result = httpManager.httpGetSend(null);
		try {
			result = result.substring(result.indexOf(">window._sharedData"));
			
			result = result.substring(0, result.indexOf(";</script>"));
			
			result = result.substring(result.indexOf("=")+1);
			
			JSONParser parsor = new JSONParser();
		
			JSONObject order_json = (JSONObject)parsor.parse(result);
			
			JSONObject entry_data = (JSONObject)order_json.get("entry_data");
			
			JSONArray ProfilePage = (JSONArray)entry_data.get("ProfilePage");
			
			JSONObject one_profile = (JSONObject)ProfilePage.get(0);
			
			JSONObject graphql = (JSONObject)one_profile.get("graphql");
			
			JSONObject user = (JSONObject)graphql.get("user");
			
			return_usr_id = (String)user.get("id");
			
			System.out.println("return_usr_id = " + usr_id);
			
			JSONObject edge_owner_to_timeline_media = (JSONObject)user.get("edge_owner_to_timeline_media");
			
			JSONArray edges = (JSONArray)edge_owner_to_timeline_media.get("edges");
			
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
					int update_cnt = updateSnsUserInfoLastUpdateTime(one_vo);
					//int update_cnt = 1;
					
					//푸쉬 보내기
					if(update_cnt > 0) {
						System.out.println("push 보내기");
						
						String message = one_vo.getUserId() + " 님의 인스타그램 사진이 등록되었습니다.";
						
						TelegramMessage.funcTelegram(message);
						
						HashMap<String,Object> main_param = new HashMap<>();
						
						String photo_url = "https://www.instagram.com/p/"+node.get("shortcode")+"/?utm_source=ig_web_copy_link";
						
						main_param.put("url", photo_url);
						
						String img_src = (String)node.get("thumbnail_src");
						
						main_param.put("large_icon", img_src);
				    	main_param.put("big_picture", img_src);
						
						
						oneSignalMessageService.send_message(data_param , message , main_param, OneSignalSegment.Instagram);
					}
					
				}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			L.info("오류["+usr_id+"] = " + result);
			System.out.println(result);
		}
		
    	return return_usr_id;
    }
    
    
    /**
     * 인스타 그램 비디오(스토리) 스크래핑
     */
    public void instagram_scrap_video(String seq , HttpHost proxy) {
    	String snsKind = SnsKind.instagram.toString();
    	String mediaKind = MediaKind.video.toString();
    	
    	//쿠키 데이타 저장
    	HashMap<String,String> headerData = new HashMap<>();
		set_cookie_info(headerData , snsKind , seq);
		
		//인스타 그램 사진 크롤링 시작
		SnsUserInfoVo snsUserInfoVo = new SnsUserInfoVo();
		snsUserInfoVo.setSnsKind(snsKind);
		snsUserInfoVo.setMediaKind(mediaKind);
		
		HashMap<String,Object> data_param = new HashMap<>();
    	data_param.put(DataType.notiType.toString(), snsKind);
    	
    	HashMap<String, SnsUserInfoVo> user_info = new HashMap<>();
    	
    	List<SnsUserInfoVo> list = selectSnsUserInfo(snsUserInfoVo);
    	
    	for(SnsUserInfoVo one_vo : list) {
    		
    		user_info.put(one_vo.getId() , one_vo);
    		
    	}
    	
    	if(list.size() > 0) {
    		call_instagram_video( user_info , headerData , data_param , proxy);
    	}
    	
    }
    
    /**
     * 인스타 그램 비디오 스크래핑
     * @param one_vo
     * @param headerData
     */
    public void call_instagram_video(HashMap<String, SnsUserInfoVo> user_info , HashMap<String,String> headerData , HashMap<String,Object> data_param , HttpHost proxy ) {
    	
    	String url = "https://i.instagram.com/api/v1/feed/reels_tray/";
    	
    	HTTPUtil httpManager = new HTTPUtil(url);
		httpManager.setHeader(headerData);
		
		if(proxy != null) {
			httpManager.setProxy(proxy);
		}
		
		String result = httpManager.httpGetSend(null);
		
		
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
						int update_cnt = updateSnsUserInfoLastUpdateTime(one_info);
						
						//푸쉬 보내기
						if(update_cnt > 0) {
							System.out.println("push 보내기");
							
							String message = one_info.getUserId() + " 님의 인스타그램 스토리가 등록되었습니다.";
							
							TelegramMessage.funcTelegram(message);
							
							HashMap<String,Object> main_param = null;
							
							String pk = call_instagram_video2(one_info , headerData );
							
							if(pk.length() > 0) {
								main_param = new HashMap<>();
								String photo_url = "https://instagram.com/stories/"+one_info.getUserId()+"/"+pk+"?utm_source=ig_story_item_share&utm_medium=share_sheet";
								
								main_param.put("url", photo_url);
							}
							
							oneSignalMessageService.send_message(data_param , message , main_param, OneSignalSegment.Instagram);
						}
					}
					
				}
				
			}
			
			//라이브 방송이 있다.
			JSONArray broadcasts = (JSONArray)order_json.get("broadcasts");
			
			if(broadcasts != null && broadcasts.size() > 0) {
				
				JSONObject obj_board = (JSONObject)broadcasts.get(0);
				
				JSONObject broadcast_owner = (JSONObject)obj_board.get("broadcast_owner");
				
				String username = (String)broadcast_owner.get("username");
				
				SnsUserInfoVo one_info = (SnsUserInfoVo)user_info.get(username);
				
				if(one_info == null) {
					//System.out.println("AAAAA");
					return;
				}
				
				String message = username + " 님의 인스타그램 라이브 방송이 등록되었습니다.";
				
				TelegramMessage.funcTelegram(message);
				
				HashMap<String,Object> main_param = null;
				
				main_param = new HashMap<>();
				String photo_url = "https://instagram.com/"+username+"/live";
				
				main_param.put("url", photo_url);
				
				//System.out.println("photo_url = " + photo_url);
				
				oneSignalMessageService.send_message(data_param , message , main_param, OneSignalSegment.Instagram);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public String call_instagram_video2(SnsUserInfoVo one_vo , HashMap<String,String> headerData ) {
    	
    	String id = one_vo.getId();
    	
    	String pk = "";
    	
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
				
				
				//long device_timestamp = (Long)one_item.get("taken_at");
				
				//System.out.println(device_timestamp);
				
				pk = (String)one_item.get("pk");
				
			}
			
		} catch (Exception e) {
			
		}
    	
		return pk;
    }
}



