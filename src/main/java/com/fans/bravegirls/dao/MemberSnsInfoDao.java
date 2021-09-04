package com.fans.bravegirls.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.vo.model.CorpSnsInfoVo;
import com.fans.bravegirls.vo.model.MemberSnsInfoVo;

import java.util.List;

@Mapper
@Repository
public interface MemberSnsInfoDao {
    

	//멤버들 정보 가져 오기
	List<MemberSnsInfoVo> selectMemberSnsInfo(MemberSnsInfoVo memberSnsInfoVo);
	
	//멤버들 정보 가져 오기
	List<CorpSnsInfoVo> selectCorpSnsInfo();
	
	//생일자 멤버 정보 가져오기
	MemberSnsInfoVo selectBirthDay(MemberSnsInfoVo memberSnsInfoVo);
}
