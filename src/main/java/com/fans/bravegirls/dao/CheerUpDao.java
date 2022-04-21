package com.fans.bravegirls.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.vo.model.CheerUpVo;

import java.util.List;

@Mapper
@Repository
public interface CheerUpDao {
    

    //응원 게시물 조회
    List<CheerUpVo> selectCheerUp();
    
}
