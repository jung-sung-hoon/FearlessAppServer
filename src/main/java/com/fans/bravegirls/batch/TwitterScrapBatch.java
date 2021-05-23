package com.fans.bravegirls.batch;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.service.TwitterService;
import com.fans.bravegirls.vo.code.MediaKind;
import com.fans.bravegirls.vo.code.SnsKind;


@RequiredArgsConstructor
@Component
@Transactional
public class TwitterScrapBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TwitterService twitterService;

    /**
     * 5분마다 작동
     */
    @Scheduled(cron = "0 */5 * * * *")
    public void twitter_tw() {

    	String snsKind = SnsKind.twitter.toString();
    	String mediaKind = MediaKind.tw.toString();
    	
        L.info("["+snsKind+" - " + mediaKind + " 스크래핑 시작]");
        
        twitterService.twitter_tw();
        
        L.info("["+snsKind+" - " + mediaKind + " 스크래핑 종료]");
    }
    
    

}
