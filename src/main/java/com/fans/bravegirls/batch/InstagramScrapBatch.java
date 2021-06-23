package com.fans.bravegirls.batch;

import lombok.RequiredArgsConstructor;

import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.service.InstagramService;
import com.fans.bravegirls.vo.code.MediaKind;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.ProxyServerVo;


@RequiredArgsConstructor
@Component
@Profile("prod")
public class InstagramScrapBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    @Autowired
    InstagramService instagramService;

    /**
     * 5분마다 작동 , 짝수 시
     */
    //@Scheduled(cron = "0 */5 */2 * * *")
    //@Scheduled(cron = "0 */5 */3 * * *")
    @Scheduled(cron = "0 */5 00-02 * * *")
    public void instagram_scrap_photo_0() {

    	String seq = "1";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    /**
     * 5분마다 작동 , 홀수 시
     */
    //@Scheduled(cron = "0 */5 1-23/2 * * *")
    //@Scheduled(cron = "0 */5 1-23/3 * * *")
    @Scheduled(cron = "0 */5 03-05 * * *")
    public void instagram_scrap_photo_1() {

    	String seq = "2";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    /**
     * 5분마다 작동 , 홀수 시
     */
    //@Scheduled(cron = "0 */5 1-23/2 * * *")
    @Scheduled(cron = "0 */5 06-08 * * *")
    public void instagram_scrap_photo_2() {

    	String seq = "3";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    
    //@Scheduled(cron = "0 */10 19-23 * * *")
    @Scheduled(cron = "0 */5 09-11 * * *")
    public void instagram_scrap_photo_3() {

    	String seq = "4";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    @Scheduled(cron = "0 */5 12-14 * * *")
    public void instagram_scrap_photo_4() {

    	String seq = "1";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    @Scheduled(cron = "0 */5 15-17 * * *")
    public void instagram_scrap_photo_5() {

    	String seq = "2";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    @Scheduled(cron = "0 */5 18-20 * * *")
    public void instagram_scrap_photo_6() {

    	String seq = "3";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    @Scheduled(cron = "0 */5 21-23 * * *")
    public void instagram_scrap_photo_7() {

    	String seq = "4";
    	
    	String proxy_seq = "0";
    	
    	run_scrap(seq , proxy_seq);
    }
    
    
    private void run_scrap(String seq, String proxy_seq) {
    	//프로시 정보 조회
    	ProxyServerVo proxyServerVo = new ProxyServerVo();
    	proxyServerVo.setSeq(proxy_seq);
    	ProxyServerVo proxy_info = instagramService.selectProxyServer(proxyServerVo);
    	
    	HttpHost proxy = null;
    	
    	if(proxy_info != null) {
    		//proxy = new HttpHost(proxy_info.getServerIp(), Integer.parseInt(proxy_info.getServerPort()));
    	}
    	
    	//사진 크롤링
    	String sns_kind = SnsKind.instagram.toString();
    	String media_type = MediaKind.photo.toString();
    	
        L.info("["+seq+" : "+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_photo(seq , proxy);
        
        L.info("["+seq+" : "+sns_kind+" - " + media_type + " 스크래핑 종료]");
        
        try {
    		Thread.sleep(2000);
    	} catch (Exception e) {
    		
    	}
        
        //비디오 크롤링
        sns_kind = SnsKind.instagram.toString();
    	media_type = MediaKind.video.toString();
    	
        L.info("["+seq+" : "+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_video(seq , proxy);
        
        L.info("["+seq+" : "+sns_kind+" - " + media_type + " 스크래핑 종료]");
    	
    }
    
    
    
    
}
