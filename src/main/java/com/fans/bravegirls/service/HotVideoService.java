package com.fans.bravegirls.service;

import java.util.List;
import com.fans.bravegirls.dao.HotVideoDao;
import com.fans.bravegirls.vo.model.HotVideoTagVo;
import com.fans.bravegirls.vo.model.HotVideoVo;
import com.fans.bravegirls.vo.model.PageHotVideoVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotVideoService {
    private final HotVideoDao hotVideoDao;

    public List<HotVideoVo> selectHotVideosHavingTag(PageHotVideoVo pageHotVideoVo) {
        List<HotVideoVo> hotVideoVos = hotVideoDao.selectHotVideosHavingTag(pageHotVideoVo);

        for (HotVideoVo hotVideoVo : hotVideoVos) {
            hotVideoVo.setTags(hotVideoDao.selectTagsOfHotVideo(hotVideoVo.getId()));
        }

        return hotVideoVos;
    }
    
    
    public int selectHotVideosHavingTagCnt(PageHotVideoVo pageHotVideoVo) {
    	return hotVideoDao.selectHotVideosHavingTagCnt(pageHotVideoVo);
    }

    public List<HotVideoTagVo> selectAllHotVideoTags() {
        return hotVideoDao.selectAllHotVideoTags();
    }

}
