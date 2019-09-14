package com.tutu.db.homebook;

import lombok.Data;

import java.io.Serializable;

@Data
public class HomeBookPart implements Serializable {
    private Integer buyNum;
    private String grade;
    private Long id;
    private Integer sort;
    private Integer canLock;
    private String textBookName;
    private Integer useCondition;
    private String icon;
}
