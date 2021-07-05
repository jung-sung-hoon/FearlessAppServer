package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class PushHistoryVo {
    private String seq;
    private String segment;
    private String message;
    private String url;
    private String createdDate;
    
}
