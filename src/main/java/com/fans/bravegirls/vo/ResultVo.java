package com.fans.bravegirls.vo;

import lombok.Data;

@Data
public class ResultVo {
    private String code;
    private String message;
    private String datetime;
    private String offset;
    private String size;
    private Object data;
    private String total;
}
