package com.tutu2.db.app;

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
 *     time   : 2018/8/16 14:12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "user")
@EqualsAndHashCode(callSuper = false)
public class UserDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private Date updatedAt;
    private String token;
    @ApiModelProperty(name = "operatorId",value = "系统操作人ID")
    private Integer operatorId;
    private String tags; /*** 用户标签冗余设计，注意一定要使用标准的json 数组格式，否则解析时会出错 */
    @ApiModelProperty(name = "openId",value = "微信 openId")
    private String openId;

}
