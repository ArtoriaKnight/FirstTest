package com.tutu2.db.studycenter;

import lombok.Data;

@Data
public class PartCoinInfo {
    private Long partId;
    private Integer passAmount; /*** part游戏通关记录中的最高分 */
}
