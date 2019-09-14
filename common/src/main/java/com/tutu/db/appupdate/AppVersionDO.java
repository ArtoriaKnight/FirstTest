package com.tutu.db.appupdate;

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
 *     @author : Tuo
 *     e-mail : 839539179@qq.com
 *     time   : 2018/8/17 10:38
 *     desc   : App版本
 *     version: 1.0
 * </pre>
 */

@SuppressWarnings("serial")
@Data
@Table(name = "app_version")
@EqualsAndHashCode(callSuper = false)
public class AppVersionDO implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ApiModelProperty(name = "appTypeId",value = "app类型Id")
    private Long appTypeId;


    @ApiModelProperty(name = "versionName",value = "版本名称")
    private String versionName;


    @ApiModelProperty(name = "apkUrl",value = "apk下载地址")
    private String apkUrl;


    @ApiModelProperty(name = "forceUpdate",value = "是否需要强制更新 1 不需要 2 需要")
    private Integer forceUpdate;


    @ApiModelProperty(name = "updateDescribe",value = "版本更新描述 格式：1 添加用户模块#2 添加商城模块")
    private String updateDescribe;

    @ApiModelProperty(name = "status",value = "1正常 2 不可用 3删除")
    private Integer status;


    @ApiModelProperty(name = "createdAt",value = "添加时间")
    private Date createdAt;
    
    @ApiModelProperty(name = "iosAudit",value = "审核状态 IOS独有字段  1审核中  2未审核")
    private Integer iosAudit;
    
    @ApiModelProperty(name = "deviceType",value = "设备类型  ")
    private String deviceType;

}
