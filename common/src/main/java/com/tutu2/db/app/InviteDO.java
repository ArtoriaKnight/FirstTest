package com.tutu2.db.app;

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
 *     time   : 2018/11/30 17:46
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Table(name = "invite_record")
@ApiModel("邀请记录")
@Data
public class InviteDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(name = "fromUser",value = "邀请人")
    private Long fromUser;
    @ApiModelProperty(name = "fromUser",value = "被邀请人")
    private Long toUser;
    @ApiModelProperty(name = "createdAt",value = "发起邀请时间")
    private Date createdAt;
}
