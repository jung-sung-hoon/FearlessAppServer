package com.fans.bravegirls.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.AboutInfoService;
import com.fans.bravegirls.service.MemberSnsInfoService;
import com.fans.bravegirls.service.NoticeService;
import com.fans.bravegirls.vo.model.AboutInfoVO;
import com.fans.bravegirls.vo.model.MemberSnsInfoVo;
import com.fans.bravegirls.vo.model.NoticeVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotiController extends BaseRestController {

	@Autowired
    private MemberSnsInfoService memberSnsInfoService;

	@Autowired
	private NoticeService noticeService;
	
    /**
     * noti 정보
     * - 생일자
     * - 공지 1건
     * @param request
     * @return
     */
    @GetMapping(value = "/notiInfo")
    public ResponseEntity<?> getHotVideos(HttpServletRequest request) {
        ipCheck(request);

        //생일자 정보
        MemberSnsInfoVo birth_day_member = memberSnsInfoService.selectBirthDay();

        HashMap<String, Object> result_map = new HashMap<>();
        
        result_map.put("birth_day_yn", "Y");
        
        if(birth_day_member == null) {
        	result_map.put("birth_day_yn", "N");
        }

        result_map.put("birth_day_map", birth_day_member);
        
        
        NoticeVo sch_noti = noticeService.selectNoticePop();
        
        result_map.put("notice_yn", "Y");
        
        if(sch_noti == null) {
        	result_map.put("notice_yn", "N");
        }
        
        result_map.put("notice_map", sch_noti);

        return success(result_map);
    }

}
