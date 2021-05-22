package com.fans.bravegirls.vo.model;

import java.util.List;
import lombok.Data;

@Data
public class HotVideoVo {
    private int id;
    private String code;
    private String title;
    private String playTime;
    private String uploadDate;
    private String channelTitle;

    private List<HotVideoTagVo> tags;
}
