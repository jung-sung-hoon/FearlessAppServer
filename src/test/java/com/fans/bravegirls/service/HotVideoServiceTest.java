package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
import com.fans.bravegirls.vo.model.PageHotVideoVo;

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
public class HotVideoServiceTest {
    @Autowired
    private HotVideoService hotVideoService;

    @Test
    public void selectHotVideosHavingTag() {

    	int tagId = 1;
    	int offSet = 1;
    	int pageSize = 2;
    	
    	PageHotVideoVo pageHotVideoVo = new PageHotVideoVo();
        pageHotVideoVo.setId(tagId);
        
        offSet = (offSet - 1) * pageSize;
        
        pageHotVideoVo.setOffSet(offSet);
        pageHotVideoVo.setPageSize(pageSize+1);
    	
        List<HotVideoVo> hotVideoVos = hotVideoService.selectHotVideosHavingTag(pageHotVideoVo);

        Assert.assertNotNull(hotVideoVos);
        Assert.assertNotNull(hotVideoVos.get(0).getTags());
        
        System.out.println("hotVideoVos size = " + hotVideoVos.size());
        System.out.println(hotVideoVos);
        
        if(hotVideoVos.size() > pageSize) {
        	hotVideoVos.remove(pageSize);
        }
        
        System.out.println("hotVideoVos size = " + hotVideoVos.size());
        System.out.println(hotVideoVos);
        
        //총건수
        System.out.println(hotVideoService.selectHotVideosHavingTagCnt(pageHotVideoVo));
    }

    //@Test
    public void selectAllHotVideoTags() {

        List<HotVideoTagVo> hotVideoTagVos = hotVideoService.selectAllHotVideoTags();

        Assert.assertNotNull(hotVideoTagVos);
        System.out.println(hotVideoTagVos);
    }
}
