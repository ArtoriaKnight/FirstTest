package com.tutu.db;

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
 *     time   : 2018/9/30 04:11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "course_user")
public class CourseUserDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long textbookId;
    private Long userId;
    private Date buyAt;
    private Date beginAt;
    private Date endAt;
    private Integer status;
    private Long latestUnit; // 最后一次进入的part所属的unit
    private Long latestPart; // 最后一次进入的part

    @ApiModelProperty(name = "operatorId",value = "系统操作人ID")
    private Integer operatorId;
}
