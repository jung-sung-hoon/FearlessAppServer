package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.ChartService;
import com.fans.bravegirls.vo.model.ChartVo;
import com.fans.bravegirls.vo.model.PageChartVo;
import com.fans.bravegirls.vo.model.PageInfoVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChartController extends BaseRestController {

    private final ChartService chartService;


    @GetMapping(value = "/chart")
    public ResponseEntity<?> getHotVideos(HttpServletRequest request,
            @RequestParam(value = "page") int page
            ,@RequestParam(value = "size") int size) {
        ipCheck(request);

        
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
        
        List<ChartVo> result = chartService.selectChart(pageChartVo);

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);
        
        //다음 페이지 있나의 여부
        result_map.put("nextYn", "N");
        
        if(result.size() > size) {
        	result_map.put("nextYn", "Y");
        	result.remove(size);
        }
        
        result_map.put("nextPageNum", (start_page+1));
        
        PageInfoVo pageInfo = new PageInfoVo();
        pageInfo.setPage(start_page);
        pageInfo.setSize(size);
        pageInfo.setTotal(chartService.selectChartCnt());
        
        result_map.put("pageInfo", pageInfo );
        
        return success(result_map);
    }

}
