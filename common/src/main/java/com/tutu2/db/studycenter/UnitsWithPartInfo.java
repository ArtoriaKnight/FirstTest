package com.tutu2.db.studycenter;

import lombok.Data;

@Data
public class UnitsWithPartInfo {
    private Long unitId;
    private String text;
    private Long partId;
    private String icon;
    private Integer canLock;
    private String title;
    private String tips;
    private Integer topScore;
    private Integer state;
    private Integer passAmount;
    private Integer maxAmount;
    private Boolean isPass;
}
