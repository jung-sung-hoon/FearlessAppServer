package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.TodayIssueService;
import com.fans.bravegirls.vo.model.TodayIssueVo;
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
public class TodayIssueController extends BaseRestController {
    private final TodayIssueService todayIssueService;

    @GetMapping(value = "/todayIssues")
    public ResponseEntity<?> scheduled(HttpServletRequest request,
            @RequestParam(value = "view_cal", defaultValue = "") String view_cal) {
        ipCheck(request);

        List<TodayIssueVo> result = todayIssueService.selectTodayIssuesByStartDate(view_cal);

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);

        return success(result_map);
    }
}
