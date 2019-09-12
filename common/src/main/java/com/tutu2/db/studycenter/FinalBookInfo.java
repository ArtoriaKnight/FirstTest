package com.tutu2.db.studycenter;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

@Data
public class FinalBookInfo implements Serializable {
    private Long textBookId;
    private String textBookName;
    private Long latestUnit;
    private Long latestPart;
    private LinkedList<FinalUnitInfo> unitList = new LinkedList<>();
}
