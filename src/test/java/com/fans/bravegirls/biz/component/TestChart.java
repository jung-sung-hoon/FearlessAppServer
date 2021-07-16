package com.fans.bravegirls.biz.component;


import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fans.bravegirls.service.ChartService;
import com.fans.bravegirls.vo.model.ChartVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
import com.fans.bravegirls.vo.model.PageChartVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestChart {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    ChartService chartService;
    
    @Test
    public void selectChart() {
    	
    	int page = 1;
    	int size = 2;
    	
    	if(size <= 0) {
        	size = 20;
        }
        
        if(size > 100) {
        	size = 100;
        }
        
        int start_page = page;
        
        page = (page - 1) * size;
    	
    	PageChartVo pageChartVo = new PageChartVo();
        
    	pageChartVo.setOffSet(page);
    	pageChartVo.setPageSize(size+1);
    	
        List<ChartVo> char_list = chartService.selectChart(pageChartVo);

        
        System.out.println("size = " + char_list.size());
        System.out.println(char_list);
        
        if(char_list.size() > size) {
        	char_list.remove(size);
        }
        
        System.out.println("size = " + char_list.size());
        System.out.println(char_list);
        
        //총건수
        System.out.println(chartService.selectChartCnt());
    	
    }
    
    
    

}
