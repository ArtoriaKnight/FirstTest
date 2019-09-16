package com.tutu2.db.homebook;

import lombok.Data;

@Data
public class HomeBookDTO {
    /*** unit */
    private Long unitId;
    private String text;
    private Integer sort;
    private String unitIcon;
    /*** part */
    private Integer canLock;
    private String grade;
    private Long partId;
    private Integer partSort;
    private String title;
    private String tips;
    private String partIcon;
}
