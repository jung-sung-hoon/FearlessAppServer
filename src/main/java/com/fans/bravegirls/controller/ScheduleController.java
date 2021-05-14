package com.fans.bravegirls.controller;

import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.ScheduleService;
import com.fans.bravegirls.vo.model.ScheduleVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleController extends BaseRestController {

    private final ScheduleService scheduleService;


    @GetMapping(value = "/schedules")
    public ResponseEntity<?> scheduled(HttpServletRequest request,
            @RequestParam(value = "view_cal", defaultValue = "") String view_cal) {
        ipCheck(request);

        List<ScheduleVo> result = scheduleService.selectScheduled(view_cal);

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);

        return success(result_map);
    }
}
