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
@Table(name = "activity_study")
public class ActivityStudyDO implements Serializable {
    @Id
    private Integer id;
    @ApiModelProperty(name = "title",value = "活动标题")
    private Integer title;
    @ApiModelProperty(name = "content",value = "活动内容")
    private Integer content;
    @ApiModelProperty(name = "type",value = "活动类型")
    private Integer type;
    @ApiModelProperty(name = "createAt",value = "")
    private Date createAt;
    @ApiModelProperty(name = "startAt",value = "")
    private Date startAt;
    @ApiModelProperty(name = "endAt",value = "")
    private Date endAt;
    @ApiModelProperty(name = "updateAt",value = "")
    private Date updateAt;
    @ApiModelProperty(name = "state",value = "活动状态,0:停用,1:启用")
    private Integer state;
    @ApiModelProperty(name = "operatorId",value = "创建人ID")
    private Long operatorId;



}
