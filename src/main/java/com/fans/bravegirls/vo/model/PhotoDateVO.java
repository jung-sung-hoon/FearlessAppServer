package com.fans.bravegirls.vo.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PhotoDateVO {
    private LocalDateTime eventDate;
    private int cnt;
}
