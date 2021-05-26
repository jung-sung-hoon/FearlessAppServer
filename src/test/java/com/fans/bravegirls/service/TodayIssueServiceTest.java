package com.fans.bravegirls.service;

import java.util.List;
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
public class TodayIssueServiceTest {
    @Autowired
    private TodayIssueService todayIssueService;

    @Test
    public void selectTodayIssuesByStartDate() {
        List<TodayIssueVo> todayIssueVoList = todayIssueService.selectTodayIssuesByStartDate("202105");

        Assert.assertNotNull(todayIssueVoList);
        System.out.println(todayIssueVoList);
    }
}
