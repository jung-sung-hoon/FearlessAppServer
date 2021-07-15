package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.dao.HotVideoDao;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
import com.fans.bravegirls.vo.model.HotVideoPageable;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotVideoService {

    private final HotVideoDao hotVideoDao;


    public List<HotVideoVo> selectHotVideosHavingTag(HotVideoPageable hotVideoPageable) {
        List<HotVideoVo> hotVideoVos = hotVideoDao.selectHotVideosHavingTag(hotVideoPageable);

        for(HotVideoVo hotVideoVo : hotVideoVos) {
            hotVideoVo.setTags(hotVideoDao.selectTagsOfHotVideo(hotVideoVo.getId()));
        }

        return hotVideoVos;
    }


    public int selectHotVideosHavingTagCnt(HotVideoPageable hotVideoPageable) {
        return hotVideoDao.selectHotVideosHavingTagCnt(hotVideoPageable);
    }


    public List<HotVideoTagVo> selectAllHotVideoTags() {

        List<HotVideoTagVo> hotVideoTagVos = hotVideoDao.selectAllHotVideoTags();

        HotVideoTagVo allHotVideoTagVo = new HotVideoTagVo();
        allHotVideoTagVo.setId(0);
        allHotVideoTagVo.setTitle("전체");

        HotVideoPageable hotVideoPageable = new HotVideoPageable();
        hotVideoPageable.setTagId(0);
        hotVideoPageable.setPageSize(100000);
        hotVideoPageable.setOffSet(0);

        allHotVideoTagVo.setVideoCount(this.selectHotVideosHavingTagCnt(hotVideoPageable));

        hotVideoTagVos.add(0, allHotVideoTagVo);

        return hotVideoTagVos;
    }

}
