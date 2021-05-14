package com.fans.bravegirls.batch.biz.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fans.bravegirls.batch.biz.vo.model.ScheduleVo;

import java.util.List;

@Mapper
@Repository
public interface ScheduleDao {
    
	//스케쥴 삭제
    int deleteScheduled(String regYyyymm);

    
    //스케쥴 등록
    int insertScheduled(ScheduleVo scheduleVo);
    

    //임시조회
    List<ScheduleVo> selectScheduled(String regYyyymm);
}
