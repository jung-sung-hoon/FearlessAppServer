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

    List<HotVideoTagVo> selectTagsOfHotVideo(int videoId);

    List<HotVideoTagVo> selectAllHotVideoTags();
}
