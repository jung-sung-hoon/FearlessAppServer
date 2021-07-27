package com.fans.bravegirls.vo.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class FolderVO {
    private String id;
    private String name;
    private String path;
    private int photoCnt;
    private LocalDateTime latestEventDate;
    private List<PhotoVO> photos;
}
