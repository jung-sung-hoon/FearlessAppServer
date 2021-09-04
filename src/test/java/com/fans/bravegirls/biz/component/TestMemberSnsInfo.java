package com.fans.bravegirls.biz.component;


import com.fans.bravegirls.vo.model.CorpSnsInfoVo;
import com.fans.bravegirls.vo.model.MemberSnsInfoVo;
import com.fans.bravegirls.vo.model.ScheduleVo;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.service.MemberSnsInfoService;
import com.fans.bravegirls.service.ScheduleService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;


@Slf4j
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMemberSnsInfo {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired
    MemberSnsInfoService memberSnsInfoService;
    
    
    /**
     * 타임존에 따른 시간 변환
     * @param localTime
     * @param zone
     * @return
     */
    @Test
    public void selectMemberSnsInfo() {
    	System.out.println(memberSnsInfoService.selectMemberSnsInfo());
    	
    }
    
    //@Test
    public void selectCorpSnsInfo() {
    	
    	List<CorpSnsInfoVo> list = memberSnsInfoService.selectCorpSnsInfo();
    	
    	System.out.println(list);
    	
    }
    
	/**
	 *	생일자 멤버 조회 
	 */
    //@Test
    public void selectBirthDay() {
    	
    	MemberSnsInfoVo birth_day_membe = memberSnsInfoService.selectBirthDay();
    	
    	System.out.println(birth_day_membe);
    }
}
