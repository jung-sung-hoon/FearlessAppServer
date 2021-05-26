package com.fans.bravegirls.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.vo.model.CookieInfoVo;
import com.fans.bravegirls.vo.model.SnsUserInfoVo;

import java.util.List;

@Mapper
@Repository
public interface InstagramDao {
    

	//sns 정보 가져 오기
	List<SnsUserInfoVo> selectSnsUserInfo(SnsUserInfoVo snsUserInfoVo);
	
    //크롤링할 head 정보 가져오기
    List<CookieInfoVo> selectCookieInfo(CookieInfoVo cookieInfoVo);
    
    //미디어의 최종 등록 시간을 업데이트 한다.
    int updateSnsUserInfoLastUpdateTime(SnsUserInfoVo snsUserInfoVo);
    
    //미디어의 사용자 ID 를 업데이트 한다.
    int updateSnsUserInfoId(SnsUserInfoVo snsUserInfoVo);
}
