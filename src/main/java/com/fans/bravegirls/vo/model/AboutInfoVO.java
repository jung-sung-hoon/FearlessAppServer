package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class AboutInfoVO {
    private String id;
    private String title;
    private String link;
    private String icon;
    private String regDate;
    
    
    public String getIcon() {
    	
    	String rtn_icon = "";
    	
    	if(icon != null) {
    		rtn_icon = icon;
    	}
    	
    	return rtn_icon;
    }
    
}
