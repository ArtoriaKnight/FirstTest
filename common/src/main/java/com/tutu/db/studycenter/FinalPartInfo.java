package com.tutu.db.studycenter;

import lombok.Data;

import java.io.Serializable;

@Data
public class FinalPartInfo implements Serializable {
    private Long partId;
    private Integer topScore;
    private String icon;
    private Integer canLock;
    private Integer passAmount = 0;
    private Integer state = 0;
    private Boolean isPass = false;
    private Integer maxAmount;
    private String title;
    private String tips;
}
