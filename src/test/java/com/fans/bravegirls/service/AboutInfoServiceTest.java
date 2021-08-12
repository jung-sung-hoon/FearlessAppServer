package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.vo.model.AboutInfoVO;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AboutInfoServiceTest {
    @Autowired
    private AboutInfoService aboutInfoService;

    @Test
    public void selectAboutInfoList() {

    	List<AboutInfoVO> result = aboutInfoService.selectAboutInfoList();
    	
    	System.out.println(result);
    }

    
}
