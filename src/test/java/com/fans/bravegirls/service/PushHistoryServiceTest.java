package com.fans.bravegirls.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fans.bravegirls.common.utils.DateUtils;
import com.fans.bravegirls.vo.code.OneSignalSegment;
import com.fans.bravegirls.vo.model.PushHistoryVo;
import com.fans.bravegirls.vo.model.TodayIssueVo;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushHistoryServiceTest {
    @Autowired
    private PushHistoryService pushHistoryService;

    //@Test
    public void selectPushHistory() {
    	List<PushHistoryVo> push_list = pushHistoryService.selectPushHistory();

        Assert.assertNotNull(push_list);
        System.out.println(push_list);
    }
    
    //@Test
    public void insertPushHistory() {
    	
    	OneSignalSegment segment = OneSignalSegment.Instagram;
    	
    	String message = "u.nalee 님의 인스타그램 스토리가 등록되었습니다.";
    	String url = "https://instagram.com/stories/u.nalee/2607551758989482447?utm_source=ig_story_item_share";
    	
    	Date today = new Date(); 
    	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	String createdDate = format1.format(today);
    	
    	PushHistoryVo pushHistoryVo = new PushHistoryVo();
    	pushHistoryVo.setSegment(segment.toString());
    	pushHistoryVo.setMessage(message);
    	pushHistoryVo.setUrl(url);
    	pushHistoryVo.setCreatedDate(createdDate);
    	
    	System.out.println(pushHistoryVo);
    	
    	pushHistoryService.insertPushHistory(pushHistoryVo);

    }
}
