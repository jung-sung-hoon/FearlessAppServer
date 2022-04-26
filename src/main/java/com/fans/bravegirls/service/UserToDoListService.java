package com.fans.bravegirls.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fans.bravegirls.dao.UserToDoListDao;
import com.fans.bravegirls.vo.model.UserToDoListVo;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserToDoListService {

    private final UserToDoListDao userToDoListDao;


    //이벤트 조회
    public List<UserToDoListVo> selectUserToDoList() {

        return userToDoListDao.selectUserToDoList();
    }
    
    
    
}



