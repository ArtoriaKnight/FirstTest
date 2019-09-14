package com.tutu.db.studycenter;

import lombok.Data;

import java.io.Serializable;

@Data
public class TextBookWithLatestMarkDTO implements Serializable {
    private Long textbookId;
    private Long userId;
    private String textbookName;
    private Long latestUnit;
    private Long latestPart;
}
