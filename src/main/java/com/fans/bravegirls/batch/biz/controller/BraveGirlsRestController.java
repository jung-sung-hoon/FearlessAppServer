package com.fans.bravegirls.batch.biz.controller;

import com.fans.bravegirls.batch.biz.controller.base.BaseRestController;
import com.fans.bravegirls.batch.biz.service.ScheduledService;
import com.fans.bravegirls.batch.biz.vo.model.ScheduleVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/api/v1/bg",  produces= MediaType.APPLICATION_JSON_VALUE)
public class BraveGirlsRestController extends BaseRestController {

    Logger L = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ScheduledService scheduledService;

    //에코
    @GetMapping(value="/echo")
    public ResponseEntity<?> echo(HttpServletRequest request) {
        ipCheck(request);
        
        return success("OK");
    }

    //일정 리로드
    @GetMapping(value="/scheduled/reload")
    public ResponseEntity<?> scheduled_reload(HttpServletRequest request) {
        ipCheck(request);
        
        scheduledService.call_scheduled();
        
        return success("OK");
    }
    
    //일정 리로드
    @GetMapping(value="/scheduled")
    public ResponseEntity<?> scheduled(HttpServletRequest request , @RequestParam(value="view_cal", defaultValue="") String view_cal) {
        ipCheck(request);
        
        List<ScheduleVo> result = scheduledService.selectScheduled(view_cal);
        
        HashMap<String,Object> result_map = new HashMap<>();
        
        result_map.put("list", result);
        
        return success(result_map);
    }
}