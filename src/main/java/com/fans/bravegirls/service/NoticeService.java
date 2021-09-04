package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.NoticeDao;
import com.fans.bravegirls.vo.model.NoticePageable;
import com.fans.bravegirls.vo.model.NoticeVo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeDao noticeDao;

    
  	//공지리스트 
  	public List<NoticeVo> selectNoticeList(NoticePageable noticePageable) {
  		return noticeDao.selectNoticeList(noticePageable);
  	}
  	
  	//메인 팝업 공지 1건
  	public NoticeVo selectNoticePop() {
  		
  		Date today = new Date(); 
    	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    	String today_str = format1.format(today);
    	
    	NoticePageable noticePageable = new NoticePageable();
    	noticePageable.setTodayDate(today_str);
    	
    	NoticeVo sch_noti = noticeDao.selectNoticePop(noticePageable);
  		
  		return sch_noti;
  	}
}



