package com.fans.bravegirls.controller;

import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.CheerUpService;
import com.fans.bravegirls.service.ScheduleService;
import com.fans.bravegirls.vo.model.CheerUpVo;
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
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class CheerUpController extends BaseRestController {

    private final CheerUpService cheerUpService;


    @GetMapping(value = "/cheerup")
    public ResponseEntity<?> scheduled(HttpServletRequest request) {
        ipCheck(request);

        List<CheerUpVo> result = cheerUpService.selectCheerUp();

        HashMap<String, Object> result_map = new HashMap<>();

        result_map.put("list", result);

        return success(result_map);
    }
}
