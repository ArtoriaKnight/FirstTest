package com.tutu2.db.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
 *     time   : 2018/12/1 20:19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@ApiModel("系统参数")
@Table(name = "sys_params")
public class SysParamsDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "inviteIcon",value = "邀请背景图")
    private String inviteIcon;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "inviteUrl",value = "邀请地址")
    private String inviteUrl;
    @ApiModelProperty(name = "createdAt",value = "创建时间")
    private Date createdAt;

}
