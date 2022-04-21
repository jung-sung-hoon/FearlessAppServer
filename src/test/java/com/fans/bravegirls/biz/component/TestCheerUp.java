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

import com.fans.bravegirls.service.CheerUpService;
import com.fans.bravegirls.vo.model.CheerUpVo;

import java.util.List;


@Slf4j
@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCheerUp {

    private Logger L = LoggerFactory.getLogger(this.getClass());
    
    
    @Autowired
    CheerUpService cheerUpService;
    
    @Test
    public void selectCheerUp() {
    	
		List<CheerUpVo> result_list = cheerUpService.selectCheerUp();
    	
    	for(CheerUpVo one_obj : result_list) {
    		System.out.println(one_obj);
    	}
    		
    }
    
    
    

}
