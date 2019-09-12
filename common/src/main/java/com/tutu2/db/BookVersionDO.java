package com.tutu2.db;

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
 *     time   : 2018/8/21 20:25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
@Data
@Table(name = "book_version")
@EqualsAndHashCode(callSuper = false)
public class BookVersionDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Range(min = 1)
    private Long id;
    @ApiModelProperty(name = "status",value = "状态1启用2禁用")
    private Integer status;
    @ApiModelProperty(name = "name",value = "版本名称")
    private String name;
    @ApiModelProperty(name = "createdAt",value = "创建时间，不用传")
    private Date createdAt;
}
