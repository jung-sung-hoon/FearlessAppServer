package com.fans.bravegirls.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.vo.model.NoticePageable;
import com.fans.bravegirls.vo.model.NoticeVo;

import java.util.List;

@Mapper
@Repository
public interface NoticeDao {
    

	//공지 리스트 
	List<NoticeVo> selectNoticeList(NoticePageable noticePageable);
	
	
	//메인 공지
	NoticeVo selectNoticePop(NoticePageable noticePageable);
}
