package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class EventsVo {
    private String id;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String image;
    private String url;
    private String regDtm;
    private String viewStart;
    private String viewEnd;
    private String isInProgress;
    private String viewCal;
}
