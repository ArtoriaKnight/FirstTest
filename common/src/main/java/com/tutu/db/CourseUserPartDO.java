package com.tutu.db;

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
@Table(name = "course_user_parts")
public class CourseUserPartDO implements Serializable {
    @Id
    private Long userId;
    @Id
    private Long partId;
    private Integer topScore;
    private Integer state;
    private Integer tempScore;
    private Long latestSubjectId;
    private Date createTime;
    private Date updateTime;

    @ApiModelProperty(name = "latestState",value = "最近学习标识,1:最近学习")
    private Integer latestState;
    @ApiModelProperty(name = "textbookId",value = "教材Id")
    private Long textbookId;
    @ApiModelProperty(name = "bookVersionId",value = "教材版本Id")
    private Long bookVersionId;

}
