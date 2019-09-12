package com.tutu2.db;

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
 *     time   : 2018/8/14 15:54
 *     desc   : 关卡
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "customs_pass")
@EqualsAndHashCode(callSuper = false)
public class CustomsPassDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 1正常2关闭
     */
    private Integer status;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String icon;
    private Date createdAt;
    private Date updatedAt;
    private String tmpTitle;
    private Long textbookId;
}
