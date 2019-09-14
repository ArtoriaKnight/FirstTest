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
@Table(name = "class_info")
public class ClassInfoDO implements Serializable {
    @Id
    private Long id;
    @ApiModelProperty(name = "className",value = "班级名称")
    private String className;
    @ApiModelProperty(name = "heedTeacherId",value = "学习活动ID")
    private Integer headTeacherId;
    @ApiModelProperty(name = "icon",value = "状态,1:启用,0:停用")
    private Integer status;
    @ApiModelProperty(name = "createAt",value = "创建时间")
    private Date createAt;
    @ApiModelProperty(name = "updateAt",value = "修改时间")
    private Date updateAt;
    @ApiModelProperty(name = "remark",value = "备注")
    private String remark;
    @ApiModelProperty(name = "operatorId",value = "操作人 ID")
    private Integer operatorId;

    public ClassInfoDO(){}

    public ClassInfoDO(String className, Integer headTeacherId, String remark){
        this.className = className;
        this.headTeacherId = headTeacherId;
        this.remark = remark;
    }

}
