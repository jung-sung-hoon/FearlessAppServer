package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class MemberSnsInfoVo {
	private String userId;
	private String userNm;
	private String birthDay;
    private String position;
    private String snsKind;
    private String aboutImg;
    private String snsUrl;
    private String birthDayImg;
    private String birthDayIcon;
    private String birthDayAboutImg;
    private String birthDayYn;
    
    public String getBirthDayImg() {
    	if(birthDayImg == null) return "";
    	return birthDayImg;
    }
    
    public String getBirthDayIcon() {
    	if(birthDayIcon == null) return "";
    	return birthDayIcon;
    }
    
    public String getBirthDayAboutImg() {
    	if(birthDayAboutImg == null) return "";
    	return birthDayAboutImg;
    }
}
