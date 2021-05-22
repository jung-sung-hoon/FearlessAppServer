package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
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

        List<HotVideoVo> hotVideoVos = hotVideoService.selectHotVideosHavingTag(1);

        Assert.assertNotNull(hotVideoVos);
        Assert.assertNotNull(hotVideoVos.get(0).getTags());
        System.out.println(hotVideoVos);
    }

    @Test
    public void selectAllHotVideoTags() {

        List<HotVideoTagVo> hotVideoTagVos = hotVideoService.selectAllHotVideoTags();

        Assert.assertNotNull(hotVideoTagVos);
        System.out.println(hotVideoTagVos);
    }
}
