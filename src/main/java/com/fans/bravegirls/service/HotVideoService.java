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
    
    
    //기존 쿼리 문제 있어서 새로 추가
    public List<HotVideoVo> selectHotVideosHavingTag2(PageHotVideoVo pageHotVideoVo) {
        List<HotVideoVo> hotVideoVos = hotVideoDao.selectHotVideosHavingTag2(pageHotVideoVo);

        for (HotVideoVo hotVideoVo : hotVideoVos) {
            hotVideoVo.setTags(hotVideoDao.selectTagsOfHotVideo(hotVideoVo.getId()));
        }

        return hotVideoVos;
    }
    
    
    public int selectHotVideosHavingTagCnt2(PageHotVideoVo pageHotVideoVo) {
    	return hotVideoDao.selectHotVideosHavingTagCnt2(pageHotVideoVo);
    }
    

    public List<HotVideoTagVo> selectAllHotVideoTags() {
        return hotVideoDao.selectAllHotVideoTags();
    }

}
