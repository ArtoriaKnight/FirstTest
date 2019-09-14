package com.tutu.db.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/18 21:52
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@ApiModel("会员权益")
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_level_info")
public class UserLevelInfoDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_level")
    @ApiModelProperty(name = "userLevel", value = "会员等级，等级id必传")
    private Integer userLevel;
    @ApiModelProperty(name = "levelName", value = "会员等级名称")
    private String levelName;
    @ApiModelProperty(name = "exprieDays", value = "过期时间，单位天,如果为0，展示为永久有效")
    private Integer exprieDays;
    @ApiModelProperty(name = "needMoney", value = "需充值金额")
    private Long needMoney;
    @ApiModelProperty(name = "explainInfo", value = "等级描述")
    private String explainInfo;
    @ApiModelProperty(name = "icon", value = "图片")
    private String icon;
    @ApiModelProperty(name = "orgMoney", value = "原始价格")
    private Long orgMoney;
    private Integer prodLevelId;
}
