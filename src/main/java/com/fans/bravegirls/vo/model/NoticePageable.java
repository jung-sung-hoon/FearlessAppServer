package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class NoticePageable {

    private String todayDate;
    private int pageSize;
    private int offSet;
}
