package com.fans.bravegirls.dao;

import java.util.List;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
import com.fans.bravegirls.vo.model.PageHotVideoVo;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HotVideoDao {
    List<HotVideoVo> selectHotVideosHavingTag(PageHotVideoVo pageHotVideoVo);

    int selectHotVideosHavingTagCnt(PageHotVideoVo pageHotVideoVo);
    
    //기존 쿼리에 영향이 가서 추가함
    List<HotVideoVo> selectHotVideosHavingTag2(PageHotVideoVo pageHotVideoVo);

    int selectHotVideosHavingTagCnt2(PageHotVideoVo pageHotVideoVo);
    
    List<HotVideoTagVo> selectTagsOfHotVideo(int videoId);

    List<HotVideoTagVo> selectAllHotVideoTags();
}
