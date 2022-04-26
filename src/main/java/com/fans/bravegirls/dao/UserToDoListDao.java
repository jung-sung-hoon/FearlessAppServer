package com.fans.bravegirls.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.vo.model.UserToDoListVo;

import java.util.List;

@Mapper
@Repository
public interface UserToDoListDao {
    

    //오늘 할일 가져 오기
    List<UserToDoListVo> selectUserToDoList();
    
}
