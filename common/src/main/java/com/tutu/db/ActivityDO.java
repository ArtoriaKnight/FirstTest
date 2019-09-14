package com.tutu.db;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

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
 *     time   : 2018/8/23 14:29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
@Data
@Table(name = "activity")
@EqualsAndHashCode(callSuper = false)
public class ActivityDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(name = "title",value = "活动标题")
    private String title;
    @ApiModelProperty(name = "icon",value = "活动图片")
    private String icon;
    @ApiModelProperty(name = "url",value = "活动链接")
    private String url;
    @Range(min = 1,max = 2)
    @ApiModelProperty(name = "status",value = "新增接口该参数表示1购买活动2分享活动;查询接口改字段表示:1启用2关闭")
    private Integer status;
    @ApiModelProperty(name = "itemId",value = "参与活动商品，如果是等级优惠活动传会员等级userLevel,如果是分享活动不用传")
    private Long itemId;
    @ApiModelProperty(name = "activeMoney",value = "活动价格，涉及到金额活动的必传，分享活动不用传")
    private Long activeMoney;
    @ApiModelProperty(name = "activeExpireDays",value = "参与活动支持续时间单位：天，30天记为一个月,以此类推")
    private Integer activeExpireDays;
    @ApiModelProperty(name = "活动详细内容，可以没有")
    private String content;
    @ApiModelProperty(name = "createdAt",value = "创建时间，不用传")
    private Date createdAt;
    @ApiModelProperty(name = "beginAt",value = "开始时间，必传")
    private Date beginAt;
    @ApiModelProperty(name = "endAt",value = "结束时间，必传")
    private Date endAt;
    private Integer prodLevelId;
    
}
