package com.fans.bravegirls.dao;

import java.util.List;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HotVideoDao {
    List<HotVideoVo> selectHotVideosHavingTag(int tagId);

    List<HotVideoTagVo> selectTagsOfHotVideo(int videoId);

    List<HotVideoTagVo> selectAllHotVideoTags();
}
