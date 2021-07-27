package com.fans.bravegirls.vo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoPageable {

    private String folderId;
    private int pageSize;
    private int offSet;
}
