package com.tutu.db;

import io.swagger.annotations.ApiModel;
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
 *     time   : 2018/8/14 15:42
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "textbook")
@ApiModel
public class TextBookDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 年级id
     */
    private Long gradeId;
    /**课程包ID*/
    private Long bagId;
    /**
     * 1正常2下架
     */
    private Integer status;

    private String name;

    private String icon;

    private Date createdAt;

    private Long bookVersionId;

    private Integer type;

    private Integer sort;

    /** 添加链接字段 */
    private String link;
}
