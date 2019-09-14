package com.tutu.db;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/11/21 21:55
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
@Data
@ApiModel("课程包")
@Table(name = "course_bag")
public class CourseBagDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(name = "title",value = "课程包标题")
    private String title;
    @ApiModelProperty(name = "icon",value = "图标")
    private String icon;
    @ApiModelProperty(name = "status",value = "1启用2禁用")
    private Integer status;
    @ApiModelProperty(name = "textBookDOS",value = "精品课程列表")
    private List<TextBookDO> textBookDOS;
    @ApiModelProperty(name = "sort",value = "排序字段,升序")
    private Integer sort;
    private Integer invisible = 0; /*** 是否隐藏 0：否 1：是 */
}
