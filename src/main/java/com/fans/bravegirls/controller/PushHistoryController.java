package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.PushHistoryService;
import com.fans.bravegirls.vo.model.PushHistoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class PushHistoryController extends BaseRestController {
    private final PushHistoryService pushHistoryService;

    @GetMapping(value = "/pushHistory")
    public ResponseEntity<?> selectPushHistory(HttpServletRequest request) {
        ipCheck(request);

        List<PushHistoryVo> result = pushHistoryService.selectPushHistory();

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);

        return success(result_map);
    }
}
