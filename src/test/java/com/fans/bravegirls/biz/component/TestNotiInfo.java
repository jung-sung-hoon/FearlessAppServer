package com.fans.bravegirls.biz.component;


import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.service.NoticeService;



@Slf4j
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestNotiInfo {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired
    NoticeService noticeService;
    
    
    /**
     * 공지사항 1건 조회
     * @param localTime
     * @param zone
     * @return
     */
    @Test
    public void selectNoticePop() {
    	System.out.println(noticeService.selectNoticePop());
    	
    }
    
    
}
