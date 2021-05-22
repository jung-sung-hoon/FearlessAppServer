package com.fans.bravegirls.dao;

import com.fans.bravegirls.vo.model.ScheduleVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
