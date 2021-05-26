package com.fans.bravegirls.batch;

import lombok.RequiredArgsConstructor;
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


@RequiredArgsConstructor
@Component
@Transactional
@Profile("prod")
public class InstagramScrapBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    @Autowired
    InstagramService instagramService;

    /**
     * 5분마다 작동 , 짝수 시
     */
    @Scheduled(cron = "0 */5 */2 * * *")
    public void instagram_scrap_photo_even() {

    	String seq = "0";
    	
    	//사진 크롤링
    	String sns_kind = SnsKind.instagram.toString();
    	String media_type = MediaKind.photo.toString();
    	
        L.info("[짝수 : "+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_photo(seq);
        
        L.info("[짝수 : "+sns_kind+" - " + media_type + " 스크래핑 종료]");
        
        try {
    		Thread.sleep(2000);
    	} catch (Exception e) {
    		
    	}
        
        //비디오 크롤링
        sns_kind = SnsKind.instagram.toString();
    	media_type = MediaKind.video.toString();
    	
        L.info("[짝수 : "+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_video(seq);
        
        L.info("[짝수 : "+sns_kind+" - " + media_type + " 스크래핑 종료]");
    }
    
    /**
     * 5분마다 작동 , 홀수 시
     */
    @Scheduled(cron = "0 */5 1-23/2 * * *")
    public void instagram_scrap_photo_odd() {

    	String seq = "1";
    	
    	//사진 크롤링
    	String sns_kind = SnsKind.instagram.toString();
    	String media_type = MediaKind.photo.toString();
    	
        L.info("[홀수 : "+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_photo(seq);
        
        L.info("[홀수 : "+sns_kind+" - " + media_type + " 스크래핑 종료]");
        
        try {
    		Thread.sleep(2000);
    	} catch (Exception e) {
    		
    	}
        
        //비디오 크롤링
        sns_kind = SnsKind.instagram.toString();
    	media_type = MediaKind.video.toString();
    	
        L.info("[홀수 : "+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_video(seq);
        
        L.info("[홀수 : "+sns_kind+" - " + media_type + " 스크래핑 종료]");
    }
    
    
    
}
