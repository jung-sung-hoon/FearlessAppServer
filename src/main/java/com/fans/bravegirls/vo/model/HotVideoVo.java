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


    public String getThumbnailImg() {
        return "https://img.youtube.com/vi/" + this.code + "/0.jpg";
    }


    private List<HotVideoTagVo> tags;
}
