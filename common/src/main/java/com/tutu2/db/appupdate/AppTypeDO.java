package com.tutu2.db.appupdate;

/**
 * <pre>
 *     @author : Tuo
 *     e-mail : 839539179@qq.com
 *     time   : 2018/8/17 10:33
 *     desc   : app 类型
 *     version: 1.0
 * </pre>
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "app_type")
@EqualsAndHashCode(callSuper = false)
public class AppTypeDO implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ApiModelProperty(name = "name",value = "类型名称")
    private String name;

    @ApiModelProperty(name = "status",value = "状态 1 正常 2不可用 3删除")
    private Integer status;

    @ApiModelProperty(name = "createdAt",value = "添加时间")
    private Date createdAt;

}
