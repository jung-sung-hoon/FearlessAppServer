package com.fans.bravegirls.controller;

import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReloadController extends BaseRestController {

    private final ScheduleService scheduleService;


    //일정 리로드
    @PatchMapping(value = "/schedules/reload")
    public ResponseEntity<?> scheduled_reload(HttpServletRequest request) {
        ipCheck(request);

        scheduleService.call_scheduled();

        return success("OK");
    }
}
