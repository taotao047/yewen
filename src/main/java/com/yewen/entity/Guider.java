package com.yewen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guider {
    private String guiderName;
    private String guiderMajor;
    private Boolean state;
}
