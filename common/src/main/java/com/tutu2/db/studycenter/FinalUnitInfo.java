package com.tutu2.db.studycenter;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

@Data
public class FinalUnitInfo implements Serializable {
    private Long unitId;
    private String text;
    private LinkedList<FinalPartInfo> partList = new LinkedList<>();
}
