package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.CheerUpDao;
import com.fans.bravegirls.vo.model.CheerUpVo;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CheerUpService {

    private final CheerUpDao cheerUpDao;


    //이벤트 조회
    public List<CheerUpVo> selectCheerUp() {

        return cheerUpDao.selectCheerUp();
    }
    
    
    
}



