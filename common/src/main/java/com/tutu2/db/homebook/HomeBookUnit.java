package com.tutu2.db.homebook;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

@Data
public class HomeBookUnit implements Serializable {
    private Long unitId;
    private String text;
    private Integer sort;
    private String icon;
    private LinkedList<HomeBookPart> partList = new LinkedList<>();
}
