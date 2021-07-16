package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.MemberSnsInfoService;
import com.fans.bravegirls.service.PushHistoryService;
import com.fans.bravegirls.vo.model.CorpSnsInfoVo;
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
public class MemberSnsInfoController extends BaseRestController {
    private final MemberSnsInfoService memberSnsInfoService;

    @GetMapping(value = "/sns_info")
    public ResponseEntity<?> sns_list(HttpServletRequest request) {
        ipCheck(request);

        List<HashMap<String,Object>> member_list = memberSnsInfoService.selectMemberSnsInfo();
        
        List<CorpSnsInfoVo> corp_list  = memberSnsInfoService.selectCorpSnsInfo();
        

        HashMap<String, Object> result_map = new HashMap<>();
        result_map.put("member_sns", member_list);
        result_map.put("corp_sns", corp_list);

        return success(result_map);
    }
}
