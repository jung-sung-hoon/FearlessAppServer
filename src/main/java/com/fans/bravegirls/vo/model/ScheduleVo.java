package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class ScheduleVo {
    private String scheduleId;
    private String title;
    private String startTime;
    private String endTime;
    private String startLocalTime;
    private String endLocalTime;
    private String allDay;
    private String description;
    private String image;
    private String location;
    private String categoryId;
    private String name;
    private String color;
    private String timezone;
    private String regYyyymm;
    private String viewCal;
}
