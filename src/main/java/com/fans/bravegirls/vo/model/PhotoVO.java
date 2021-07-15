package com.fans.bravegirls.vo.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PhotoVO {
    private long seq;
    private String id;
    private String name;
    private String mimeType;
    private LocalDateTime eventDate;
}
