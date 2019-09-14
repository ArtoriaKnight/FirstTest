package com.tutu.db;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/22 15:09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "orders")
@EqualsAndHashCode(callSuper = false)
public class OrdersDO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long itemId;

    private String itemName;

    private String orderNo;

    private String outNo;

    private Long orderAmount;

    private Integer payType;

    private Date createdAt;

    private Integer orderStatus;

    private Date payTime;

    private String cancelReason;

    private Date updatedAt;

    private Long activityId;

    private Long textbookId;

    private Integer channelId; // 渠道id dynamic_settins(type = 4)

    @Transient
    private String channelKey;
    @ApiModelProperty(name = "operatorId",value = "系统操作人ID")
    private Integer operatorId;
}
