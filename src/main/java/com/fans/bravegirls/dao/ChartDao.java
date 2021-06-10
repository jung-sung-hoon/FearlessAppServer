package com.fans.bravegirls.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.vo.model.ChartVo;
import com.fans.bravegirls.vo.model.PageChartVo;

import java.util.List;

@Mapper
@Repository
public interface ChartDao {
    

    //이벤트 조회
    List<ChartVo> selectChart(PageChartVo pageChartVo);
    
    
    //총건수
    int selectChartCnt();
}
