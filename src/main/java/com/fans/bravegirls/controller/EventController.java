package com.fans.bravegirls.controller;

import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.EventsService;
import com.fans.bravegirls.vo.code.EventCategory;
import com.fans.bravegirls.vo.model.EventsVo;

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
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController extends BaseRestController {

    private final EventsService eventsService;


    @GetMapping(value = "/events")
    public ResponseEntity<?> getEvents(HttpServletRequest request, @RequestParam(value = "category", required = false)
            EventCategory category) {
        ipCheck(request);

        List<EventsVo> result = eventsService.selectEventsInProgress(category);

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);

        return success(result_map);
    }
}
