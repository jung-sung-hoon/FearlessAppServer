package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.fans.bravegirls.dao.AboutInfoDao;
import com.fans.bravegirls.vo.model.AboutInfoVO;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AboutInfoService {

    private final AboutInfoDao aboutInfoDao;


    public List<AboutInfoVO> selectAboutInfoList() {
        return aboutInfoDao.selectAboutInfoList();
    }
    
    
    
}



