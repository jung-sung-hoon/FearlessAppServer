package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.PushHistoryDao;
import com.fans.bravegirls.vo.model.PushHistoryVo;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PushHistoryService {

    private final PushHistoryDao pushHistoryDao;


    /**
     * 푸시 히스토리 검색
     * @return
     */
    public List<PushHistoryVo> selectPushHistory() {
        return pushHistoryDao.selectPushHistory();
    }
    
    /**
     * 푸시 히스토리 등록
     * @param pushHistoryVo
     * @return
     */
    public int insertPushHistory(PushHistoryVo pushHistoryVo) {
    	return pushHistoryDao.insertPushHistory(pushHistoryVo);
    }
}



