package com.tutu.db;

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
 * 用户使用的客户端信息记录 user_client_info
 */
@SuppressWarnings("serial")
@Data
@Table(name = "user_client_info")
@EqualsAndHashCode(callSuper = false)
public class UserClientInfoDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // 用户id
    private String deviceType; // 用户使用客户端硬件信息
    private String version; // 用户使用App的版本
    private Date createTime;
    private Date updateTime;

    @ApiModelProperty(name = "promoteId",value = "推广人ID")
    private Long promoterId;
    @ApiModelProperty(name = "deviceNum",value = "设备号")
    private String deviceNum;
}
