package com.tutu2.db;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/9/30 04:11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "activity_study_user")
public class ActivityStudyUserDO implements Serializable {
    @Id
    private Long id;
    @ApiModelProperty(name = "userId",value = "活动标题")
    private Long userId;
    @ApiModelProperty(name = "realName",value = "活动内容")
    private String realName;
    @ApiModelProperty(name = "icon",value = "头像")
    private String icon;
    @ApiModelProperty(name = "activityStudyId",value = "学习活动ID")
    private Integer activityStudyId;
    @ApiModelProperty(name = "recordTime",value = "记录时间")
    private Date recordTime;
    @ApiModelProperty(name = "partId",value = "")
    private Long partId;
    @ApiModelProperty(name = "score",value = "得分")
    private Integer score;



}
