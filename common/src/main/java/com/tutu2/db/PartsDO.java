package com.tutu2.db;

import io.swagger.annotations.ApiModelProperty;
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
 *     time   : 2018/8/14 15:49
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "parts")
public class PartsDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 单元id
     */
    private Long unitsId;
    /**
     * 1正常2关闭
     */
    private Integer status;
    /**
     * 标题
     */
    private String title;
    /**
     * 提示
     */
    private String tips;
    /**
     * 图片
     */
    private String icon;
    private Date createdAt;

    private Integer sort;
    @ApiModelProperty(name = "canLock",value = "解锁状态：1解锁2锁住")
    private Integer canLock;
    private Integer operatorId;
}
