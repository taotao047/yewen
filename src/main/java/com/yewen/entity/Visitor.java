package com.yewen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    private String uID;
    private String uPassword;
    private String uTel;
    private String uEmail;
}
