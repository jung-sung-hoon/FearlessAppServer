package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.fans.bravegirls.dao.ChartDao;
import com.fans.bravegirls.vo.model.ChartVo;
import com.fans.bravegirls.vo.model.PageChartVo;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChartService {

    private final ChartDao chartDao;


    //이벤트 조회
    public List<ChartVo> selectChart(PageChartVo pageChartVo) {

    	List<ChartVo> list = chartDao.selectChart(pageChartVo);
    	
    	for(ChartVo on_obj : list) {
    		String no = on_obj.getNo();
    		
    		String dcUrl = "https://m.dcinside.com/board/bravegirls0409/"+no;
    		
    		on_obj.setDcUrl(dcUrl);
    	}
    	
        return list;
    }
    
    //총건수
    public int selectChartCnt() {
    	return chartDao.selectChartCnt();
    }
    
    
}



