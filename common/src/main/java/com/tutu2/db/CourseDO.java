package com.tutu2.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/9/30 01:34
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "course")
public class CourseDO implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Range(min = 1,message = "必须大于0")
    @ApiModelProperty(name = "textbookId",value = "教材id")
    private Long textbookId;
    @NotNull
    @ApiModelProperty(name = "iconDetail",value = "详情图")
    private String iconDetail;
    @ApiModelProperty(name = "iconTicket",value = "优惠券图")
    private String iconTicket;
    @Range(min = 0)
    @ApiModelProperty(name = "orgAmt",value = "原始金额")
    private Long orgAmt;
    @Range(min = 0)
    @ApiModelProperty(name = "amt",value = "实际金额")
    private Long amt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "chatNo",value = "微信号")
    private String chatNo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "teacher",value = "辅导老师")
    private String teacher;
    @Range(min = 1,message = "数量必须大于0")
    @ApiModelProperty(name = "num",value = "数量")
    private Integer num;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "beginAt",value = "开课时间",example = "2018-09-10 00:00:00")
    private Date beginAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "endAt",value = "结课时间",example = "2018-09-10 00:00:00")
    private Date endAt;
    @Range(min = 1,max = 3,message = "开课方式为1或者2,3闯关解锁")
    @ApiModelProperty(name = "type",value = "开课方式:1统一开课2购买生效3闯关解锁4统一开课闯关解锁")
    private Integer type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "saleBeginAt",value = "预售时间起",example = "2018-09-10 00:00:00")
    private Date saleBeginAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(name = "saleEndAt",value = "预售时间止",example = "2018-09-10 00:00:00")
    private Date saleEndAt;

}
