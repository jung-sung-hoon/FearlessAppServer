package com.fans.bravegirls.controller;

import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.common.exception.http.BadRequestException;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.service.ScheduleService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.SnsKind;
import com.fans.bravegirls.vo.model.ScheduleVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class OneSignalMessageController extends BaseRestController {

	private Logger L = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OneSignalMessageService oneSignalMessageService;

    @PostMapping(value = "/oneSignal/message")
    public ResponseEntity<?> oneSignalMessage(HttpServletRequest request,
    		@RequestParam(value = "push_type", defaultValue = "" ) 	String push_type,	//원 시그널 메시지 타입
            @RequestParam(value = "subject", defaultValue = "" , required=false ) 	String subject,		//제목
            @RequestParam(value = "link", defaultValue = ""    , required=false) 	String link			//url
            
    ) throws BadRequestException {
    	L.debug("subject = " + subject);
    	L.debug("link = " + link);
    	
    	L.info("subject = " + subject);
    	L.info("link = " + link);
    	
    	if(subject == null || subject.length() == 0) {
    		subject = "";
    	}
    	
    	subject = subject.trim();
    	
    	if(subject.length() == 0) {
    		throw new BadRequestException("제목을 입력해 주세요.");
    	}
    	
    	
        ipCheck(request);

        boolean isUrl = false;
        
        Decoder decoder = Base64.getDecoder();

        
        //link 는 base64 디코딩, url decoding 한다.
        if(link != null && link.length() > 0) {
        	
        	link = new String(decoder.decode(link));
        	link = URLDecoder.decode(link);
        	
        	System.out.println(link);
        	
        	isUrl = true;
        }
        
        //link 는 base64 디코딩, url decoding 한다.
        if(subject != null && subject.length() > 0) {
        	
        	subject = new String(decoder.decode(subject));
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
    	
    	oneSignalMessageService.send_message(data_param, message , null);
        
        return success("OK");
    }
}
