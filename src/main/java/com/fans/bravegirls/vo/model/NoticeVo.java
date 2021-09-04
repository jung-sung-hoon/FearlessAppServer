package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class NoticeVo {
    private String seq;
    private String title;
    private String content;
    private String regDate;
    private String otPopName;
    private String otPopUrl;
    
    
    public String getOtPopName() {
    	if(otPopName == null) return "";
    	return otPopName;
    }
    
    public String getOtPopUrl() {
    	if(otPopUrl == null) return "";
    	return otPopUrl;
    }
}
