package com.tutu2.db.studycenter;

import lombok.Data;

@Data
public class PartInfo {
    private Long partId;
    /*** part游戏中允许用户得到的最高分，需要排除Subject.excludeSubjectType集合中的类型后 * 10，数据库里直接计算 */
    private Integer maxAmount;
}
