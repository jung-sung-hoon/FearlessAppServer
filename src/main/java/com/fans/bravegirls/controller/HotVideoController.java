package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.HotVideoService;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
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
            @RequestParam(value = "tag_id") int tagId) {
        ipCheck(request);

        List<HotVideoVo> result = hotVideoService.selectHotVideosHavingTag(tagId);

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);

        return success(result_map);
    }

    @GetMapping(value = "/hotVideos/tags")
    public ResponseEntity<?> getHotVideoTags(HttpServletRequest request) {
        ipCheck(request);

        List<HotVideoTagVo> result = hotVideoService.selectAllHotVideoTags();

        return success(result);
    }
}
