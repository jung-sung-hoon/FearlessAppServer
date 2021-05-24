package com.fans.bravegirls.batch;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.service.InstagramService;
import com.fans.bravegirls.vo.code.MediaKind;
import com.fans.bravegirls.vo.code.SnsKind;


@RequiredArgsConstructor
@Component
@Transactional
public class InstagramScrapBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    @Autowired
    InstagramService instagramService;

    /**
     * 5분마다 작동
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void instagram_scrap_photo() {

    	//사진 크롤링
    	String sns_kind = SnsKind.instagram.toString();
    	String media_type = MediaKind.photo.toString();
    	
        L.info("["+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_photo();
        
        L.info("["+sns_kind+" - " + media_type + " 스크래핑 종료]");
        
        try {
    		Thread.sleep(2000);
    	} catch (Exception e) {
    		
    	}
        
        //비디오 크롤링
        sns_kind = SnsKind.instagram.toString();
    	media_type = MediaKind.video.toString();
    	
        L.info("["+sns_kind+" - " + media_type + " 스크래핑 시작]");
        
        instagramService.instagram_scrap_video();
        
        L.info("["+sns_kind+" - " + media_type + " 스크래핑 종료]");
    }
}
