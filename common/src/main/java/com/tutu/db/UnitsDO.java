package com.tutu.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/14 15:47
 *     desc   : 单元
 *     version: 1.0
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "units")
public class UnitsDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 教材id
     */
    private Long textbookId;
    /**
     * 1正常2关闭
     */
    private Integer status;
    private String text;
    private String icon;
    private Date createdAt;
    private Integer sort;
    private Integer operatorId;

}
