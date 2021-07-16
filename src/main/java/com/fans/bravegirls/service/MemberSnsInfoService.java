package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.MemberSnsInfoDao;
import com.fans.bravegirls.dao.PushHistoryDao;
import com.fans.bravegirls.vo.model.CorpSnsInfoVo;
import com.fans.bravegirls.vo.model.MemberSnsInfoVo;
import com.fans.bravegirls.vo.model.PushHistoryVo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberSnsInfoService {

    private final MemberSnsInfoDao memberSnsInfoDao;


    //멤버들 정보 가져 오기
  	public List<HashMap<String,Object>> selectMemberSnsInfo() {
  		
  		List<MemberSnsInfoVo> member_sns_list = memberSnsInfoDao.selectMemberSnsInfo();
    	
    	
    	List<HashMap<String,Object>> result_list = new LinkedList<>();
    	
    	//System.out.println(member_sns_list);
    	
    	HashMap<String,Object> member_map = new HashMap<>();
    	
    	for(MemberSnsInfoVo one_obj : member_sns_list) {
    		
    		String user_id = one_obj.getUserId();
    		
    		HashMap<String,Object> sns_info = new HashMap<>();
    		sns_info.put("snsKind", one_obj.getSnsKind());
    		sns_info.put("snsUrl", one_obj.getSnsUrl());
    		
    		LinkedList<HashMap<String,Object>> sns_list = (LinkedList)member_map.get(user_id);
    		
    		//sns 없다면 신규 등록
    		if(null == sns_list) {
    			
    			sns_list = new LinkedList<>();
    			
    			member_map.put(user_id, sns_list);
    			
    			HashMap<String,Object> user_info = new HashMap<>();
    			user_info.put("userId", user_id);
    			user_info.put("position", one_obj.getPosition());
    			user_info.put("birthday", one_obj.getBirthDay());
    			user_info.put("snsList" , sns_list);
    			
    			result_list.add(user_info);
    		}
    		
    		if(one_obj.getSnsKind() == null) {
    			
    		} else {
    			sns_list.add(sns_info);
    		}
    		
    	}
    	
    	System.out.println(result_list);
  		
  		return result_list;
  	}
    
  	//회사 정보 가져 오기
  	public List<CorpSnsInfoVo> selectCorpSnsInfo() {
  		return memberSnsInfoDao.selectCorpSnsInfo();
  	}
}



