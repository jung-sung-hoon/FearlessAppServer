package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class TodayIssueVo {
    private int id;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String image;
    private String url;
    private String regDate;
    private String allDay;
}
