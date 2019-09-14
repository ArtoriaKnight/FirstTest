package com.tutu.db;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/11/14 13:55
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "sessions")
public class SessionDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(name = "textbookId",value = "教材id",example = "PEP人教版小学三件级上")
    private Long textbookId;
    @ApiModelProperty(name = "status",value = "状态1可用2禁用;新增可不传",example = "1")
    private Integer status;
    @ApiModelProperty(name = "sort",value = "排序字段，新增可不传0-999",example = "0")
    private Integer sort;
    @ApiModelProperty(name = "title",value = "大关卡标题",example = "句子闯关")
    private String title;
    @ApiModelProperty(name = "icon",value = "大关卡图片",example = "http://example.png")
    private String icon;
}

