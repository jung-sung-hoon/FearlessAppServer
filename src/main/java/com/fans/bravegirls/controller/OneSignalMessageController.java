package com.fans.bravegirls.controller;

import com.fans.bravegirls.common.BaseRestController;
import com.fans.bravegirls.common.exception.http.BadRequestException;
import com.fans.bravegirls.common.utils.TelegramMessage;
import com.fans.bravegirls.service.OneSignalMessageService;
import com.fans.bravegirls.service.ScheduleService;
import com.fans.bravegirls.vo.code.DataType;
import com.fans.bravegirls.vo.code.OneSignalSegment;
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
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/app-api/v2", produces = MediaType.APPLICATION_JSON_VALUE)
public class OneSignalMessageController extends BaseRestController {

	private Logger L = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	OneSignalMessageService oneSignalMessageService;

    @PostMapping(value = "/oneSignal/message")
    public ResponseEntity<?> oneSignalMessage(HttpServletRequest request,
    		@RequestParam(name = "segment", defaultValue = "All" ) OneSignalSegment segment,	// 세그먼트
            @RequestParam(name = "subject", defaultValue = "" , required=false ) 	String subject,		//제목
            @RequestParam(name = "link", defaultValue = ""    , required=false) 	String link,			//url
            @RequestParam(name = "img_url", defaultValue = "" ) 	String img_url , 	//푸시 이미지 
            @RequestParam(name = "icon_url", defaultValue = "" ) 	String icon_url 	//푸시  아이콘
            
    ) throws BadRequestException {
    	L.debug("subject = " + subject);
    	L.debug("link = " + link);
    	
    	L.info("subject = " + subject);
    	L.info("link = " + link);
    	
    	L.debug("img_url = " + img_url);
    	L.debug("icon_url = " + icon_url);
    	
    	L.info("img_url = " + img_url);
    	L.info("icon_url = " + icon_url);
    	
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
        
        //img_url 는 base64 디코딩, url decoding 한다.
        if(img_url != null && img_url.length() > 0) {
        	
        	img_url = new String(decoder.decode(img_url));
        	img_url = URLDecoder.decode(img_url);
        	
        	System.out.println(img_url);
        }
         
        //icon_url 는 base64 디코딩, url decoding 한다.
        if(icon_url != null && icon_url.length() > 0) {
        	
        	icon_url = new String(decoder.decode(icon_url));
        	icon_url = URLDecoder.decode(icon_url);
        	
        	System.out.println(icon_url);
        }
        
        
        
        //link 는 base64 디코딩, url decoding 한다.
        if(subject != null && subject.length() > 0) {
        	
        	subject = new String(decoder.decode(subject));
        	subject = URLDecoder.decode(subject);
        }
        
        System.out.println("변환 = " + subject);
        	
    	//원 시그널 push 보낸다.
    	HashMap<String,Object> data_param = new HashMap<String,Object>();
    	data_param.put(DataType.isUrl.toString()    , isUrl);
    	data_param.put(DataType.url.toString()      , link);
    	
    	String message = subject;
    	
    	System.out.println("data_param = " + data_param);
    	System.out.println("message = " + message);
    	
    	HashMap<String,Object> main_param = new HashMap<>();
    	
    	main_param.put("large_icon", icon_url);
    	main_param.put("big_picture", img_url);
    	//main_param.put("url", "dcapp://"+link.replaceAll("https://", "").replaceAll("http://", "") );
    	main_param.put("url",  link);
    	
    	System.out.println("main_param = " + main_param);
    	
		TelegramMessage.funcTelegram(message + " " + URLEncoder.encode(link));
    	
    	// dcapp://m.dcinside.com/board/bravegirls0409/539833
    	
    	oneSignalMessageService.send_message(data_param, message , main_param, segment);
        
        return success("OK");
    }
    
    @GetMapping(value = "/message_test")
    public ResponseEntity<?> message_test(HttpServletRequest request,
    		@RequestParam(value = "push_type", defaultValue = "" ) 	String push_type	//원 시그널 메시지 타입
            
    ) throws BadRequestException {
    	
    	
    	String message = "";
    	String link = "";
    	String photo_url = "";
    	String big_picture = "";
    	
    	HashMap<String,Object> main_param = new HashMap<>();
    	
    	boolean isUrl = false;
    	
    	if(push_type.equals(SnsKind.instagram.toString())) {
    		
    		message = "u.nalee 님의 사진이 등록 되었습니다.[test]";
    		
    		link = "";
    		
    		photo_url = "https://www.instagram.com/p/CPnA9prFTco/?utm_source=ig_web_copy_link";
    		
   			big_picture = "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/e35/s1080x1080/198621966_976690136482964_8018834584222455912_n.jpg?tp=1&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=1&_nc_ohc=3hUz8EcP20UAX-5OuNP&edm=ABfd0MgBAAAA&ccb=7-4&oh=1f5f708515258c35a22e7a43a388706d&oe=60C71A80&_nc_sid=7bff83";
    			
    	} else if(push_type.equals(SnsKind.twitter.toString())) {
    		message = "u.nalee 님의 트윗이 등록 되었습니다.[test]";
    		
    		link = "";
    		
    		photo_url = "https://twitter.com/u_nalee_/status/1396839212008185860";
    		
    		big_picture = "https://pbs.twimg.com/media/E2KR2JLVoAEFx_b?format=jpg&name=large";
    	}
    	
    	main_param.put("url", photo_url);
    	main_param.put("large_icon", big_picture);
    	main_param.put("big_picture", big_picture);
    	
        	
    	//원 시그널 push 보낸다.
    	HashMap<String,Object> data_param = new HashMap<String,Object>();
    	data_param.put(DataType.notiType.toString() , push_type );
    	data_param.put(DataType.isUrl.toString()    , isUrl);
    	data_param.put(DataType.url.toString()      , "");
    	
    	
    	
    	System.out.println("data_param = " + data_param);
    	System.out.println("message = " + message);
    	
    	oneSignalMessageService.send_message(data_param, message , main_param, OneSignalSegment.Instagram);
        
        return success("OK");
    }
    
    @GetMapping(value = "/aos_message_test")
    public ResponseEntity<?> aos_message_test(HttpServletRequest request,
    		@RequestParam(value = "push_type", defaultValue = "" ) 	String push_type	//원 시그널 메시지 타입
            
    ) throws BadRequestException {
    	
    	L.debug("aos_message_test");
    	
    	String message = "";
    	String link = "";
    	String photo_url = "";
    	String big_picture = "";
    	
    	HashMap<String,Object> main_param = new HashMap<>();
    	
    	boolean isUrl = false;
    	
    	if(push_type.equals(SnsKind.instagram.toString())) {
    		
    		message = "u.nalee 님의 사진이 등록 되었습니다.[test]";
    		
    		link = "";
    		
    		photo_url = "https://www.instagram.com/p/CPnA9prFTco/?utm_source=ig_web_copy_link";
    		
   			big_picture = "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/e35/s1080x1080/198621966_976690136482964_8018834584222455912_n.jpg?tp=1&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=1&_nc_ohc=3hUz8EcP20UAX-5OuNP&edm=ABfd0MgBAAAA&ccb=7-4&oh=1f5f708515258c35a22e7a43a388706d&oe=60C71A80&_nc_sid=7bff83";
    			
    	} else if(push_type.equals(SnsKind.twitter.toString())) {
    		message = "u.nalee 님의 트윗이 등록 되었습니다.[test]";
    		
    		link = "";
    		
    		photo_url = "https://twitter.com/u_nalee_/status/1396839212008185860";
    		
    		big_picture = "https://pbs.twimg.com/media/E2KR2JLVoAEFx_b?format=jpg&name=large";
    	}
    	
    	main_param.put("url", photo_url);
    	main_param.put("large_icon", big_picture);
    	main_param.put("big_picture", big_picture);
    	
        	
    	//원 시그널 push 보낸다.
    	HashMap<String,Object> data_param = new HashMap<String,Object>();
    	data_param.put(DataType.notiType.toString() , push_type );
    	data_param.put(DataType.isUrl.toString()    , isUrl);
    	data_param.put(DataType.url.toString()      , "");
    	
    	
    	
    	System.out.println("data_param = " + data_param);
    	System.out.println("message = " + message);
    	
    	oneSignalMessageService.send_message_aos(data_param, message , main_param, OneSignalSegment.Instagram);
        
        return success("OK");
    }
    
    @GetMapping(value = "/ios_message_test")
    public ResponseEntity<?> ios_message_test(HttpServletRequest request,
    		@RequestParam(value = "push_type", defaultValue = "" ) 	String push_type	//원 시그널 메시지 타입
            
    ) throws BadRequestException {
    	
    	L.debug("ios_message_test");
    	
    	String message = "";
    	String link = "";
    	String photo_url = "";
    	String big_picture = "";
    	
    	HashMap<String,Object> main_param = new HashMap<>();
    	
    	boolean isUrl = false;
    	
    	if(push_type.equals(SnsKind.instagram.toString())) {
    		
    		message = "u.nalee 님의 사진이 등록 되었습니다.[test]";
    		
    		link = "";
    		
    		photo_url = "https://www.instagram.com/p/CPnA9prFTco/?utm_source=ig_web_copy_link";
    		
   			big_picture = "https://scontent-ssn1-1.cdninstagram.com/v/t51.2885-15/e35/s1080x1080/198621966_976690136482964_8018834584222455912_n.jpg?tp=1&_nc_ht=scontent-ssn1-1.cdninstagram.com&_nc_cat=1&_nc_ohc=3hUz8EcP20UAX-5OuNP&edm=ABfd0MgBAAAA&ccb=7-4&oh=1f5f708515258c35a22e7a43a388706d&oe=60C71A80&_nc_sid=7bff83";
    			
    	} else if(push_type.equals(SnsKind.twitter.toString())) {
    		message = "u.nalee 님의 트윗이 등록 되었습니다.[test]";
    		
    		link = "";
    		
    		photo_url = "https://twitter.com/u_nalee_/status/1396839212008185860";
    		
    		big_picture = "https://pbs.twimg.com/media/E2KR2JLVoAEFx_b?format=jpg&name=large";
    	}
    	
    	main_param.put("url", photo_url);
    	main_param.put("large_icon", big_picture);
    	main_param.put("big_picture", big_picture);
    	
        	
    	//원 시그널 push 보낸다.
    	HashMap<String,Object> data_param = new HashMap<String,Object>();
    	data_param.put(DataType.notiType.toString() , push_type );
    	data_param.put(DataType.isUrl.toString()    , isUrl);
    	data_param.put(DataType.url.toString()      , "");
    	
    	
    	
    	System.out.println("data_param = " + data_param);
    	System.out.println("message = " + message);
    	
    	oneSignalMessageService.send_message_ios(data_param, message , main_param, OneSignalSegment.Instagram);
        
        return success("OK");
    }
}
