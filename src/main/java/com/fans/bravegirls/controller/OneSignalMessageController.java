package com.fans.bravegirls.controller;

import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.service.ScheduleService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.ScheduleVo;
import com.sun.xml.internal.messaging.saaj.util.Base64;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class OneSignalMessageController extends BaseRestController {


	@Autowired
	OneSignalMessageService oneSignalMessageService;

    @PostMapping(value = "/oneSignal/message")
    public ResponseEntity<?> oneSignalMessage(HttpServletRequest request,
    		@RequestParam(value = "push_type", defaultValue = "") 	String push_type,	//원 시그널 메시지 타입
            @RequestParam(value = "subject", defaultValue = "") 	String subject,		//제목
            @RequestParam(value = "link", defaultValue = "") 		String link			//url
            
    ) {
        ipCheck(request);

        boolean isUrl = false;
        
        //link 는 base64 디코딩, url decoding 한다.
        if(link != null && link.length() > 0) {
        	
        	link = Base64.base64Decode(link);
        	link = URLDecoder.decode(link);
        	
        	System.out.println(link);
        	
        	isUrl = true;
        }
        
        //link 는 base64 디코딩, url decoding 한다.
        if(subject != null && subject.length() > 0) {
        	
        	subject = Base64.base64Decode(subject);
        	subject = URLDecoder.decode(subject);
        }
        
        System.out.println("변환 = " + subject);
        	
    	//원 시그널 push 보낸다.
    	HashMap<String,Object> data_param = new HashMap<String,Object>();
    	data_param.put(DataType.notiType.toString() , SnsKind.dc_noti.toString());
    	data_param.put(DataType.isUrl.toString()    , isUrl);
    	data_param.put(DataType.url.toString()      , link);
    	
    	String message = subject;
    	
    	System.out.println("data_param = " + data_param);
    	System.out.println("message = " + message);
    	
    	oneSignalMessageService.send_message(data_param, message);
        
        return success("OK");
    }
}
