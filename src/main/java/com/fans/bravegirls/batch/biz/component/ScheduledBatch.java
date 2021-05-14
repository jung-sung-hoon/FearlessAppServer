package com.fans.bravegirls.batch.biz.component;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.batch.biz.service.ScheduledService;

@RequiredArgsConstructor
@Component
@Transactional
public class ScheduledBatch {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ScheduledService scheduledService;

    /**
     * 00시에 배치 작동
     */
    @Scheduled(cron = "00 00 00 * * *")
    public void scheduled() {

        System.out.println("스크래핑 시작");
    	
        L.info("[스케쥴 스크래핑 시작 ]");
        
        scheduledService.call_scheduled();
        
    	L.info("[스케쥴 스크래핑 종료 ]");
        
    }

}
