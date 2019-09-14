package com.tutu.db;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/15 15:58
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
@Data
@Table(name = "subject_one")
@ApiModel("题目")
@EqualsAndHashCode(callSuper = false)
public class SubjectOneDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    /**素材集合*/
    @ApiModelProperty(name = "partId",value = "章节Id")
    private Long partId;
    @ApiModelProperty(name = "subjectType",value = "游戏类型")
    private Integer subjectType;
    @ApiModelProperty(name = "sessionId",value = "大关卡")
    private Integer sessionId;
    @ApiModelProperty(name = "remarks",value = "题目备注说明")
    private String remarks;
    @ApiModelProperty(name = "sourceIds",value = "素材信息")
    private String sourceIds;
    @ApiModelProperty(name = "extendMess",value = "扩展信息")
    private String extendMess;
    @ApiModelProperty(name = "createOwner",value = "创建人")
    private String createOwner;
    @ApiModelProperty(name = "createDate",value = "创建时间")
    private LocalDateTime createDate;
    /**题目顺序*/
    @ApiModelProperty(name = "sort",value = "题目顺序")
    private Integer sort;
}
