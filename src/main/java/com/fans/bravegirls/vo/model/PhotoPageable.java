package com.fans.bravegirls.vo.model;

import lombok.Data;

@Data
public class PhotoPageable {

    private String folderId;
    private int pageSize;
    private int offSet;
}
