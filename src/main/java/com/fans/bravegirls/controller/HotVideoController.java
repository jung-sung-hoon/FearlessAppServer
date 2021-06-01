package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.HotVideoService;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
import com.fans.bravegirls.vo.model.PageHotVideoVo;

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
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class HotVideoController extends BaseRestController {

    private final HotVideoService hotVideoService;


    @GetMapping(value = "/hotVideos")
    public ResponseEntity<?> getHotVideos(HttpServletRequest request,
            @RequestParam(value = "tag_id") int tagId
            ,@RequestParam(value = "off_set") int offSet
            ,@RequestParam(value = "page_size") int pageSize) {
        ipCheck(request);

        
        PageHotVideoVo pageHotVideoVo = new PageHotVideoVo();
        pageHotVideoVo.setId(tagId);
        
        if(pageSize <= 0) {
        	pageSize = 20;
        }
        
        if(pageSize > 100) {
        	pageSize = 100;
        }
        
        int start_page = offSet;
        
        offSet = (offSet - 1) * pageSize;
        
        pageHotVideoVo.setOffSet(offSet);
        pageHotVideoVo.setPageSize(pageSize+1);
        
        List<HotVideoVo> result = hotVideoService.selectHotVideosHavingTag(pageHotVideoVo);

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);
        
        //다음 페이지 있나의 여부
        result_map.put("nextYn", "N");
        
        if(result.size() > pageSize) {
        	result_map.put("nextYn", "Y");
        	result.remove(pageSize);
        }
        
        result_map.put("nextPageNum", (start_page+1));
        
        result_map.put("total_cnt", hotVideoService.selectHotVideosHavingTagCnt(pageHotVideoVo));
        
        return success(result_map);
    }

    @GetMapping(value = "/hotVideos/tags")
    public ResponseEntity<?> getHotVideoTags(HttpServletRequest request) {
        ipCheck(request);

        List<HotVideoTagVo> result = hotVideoService.selectAllHotVideoTags();

        return success(result);
    }
}
