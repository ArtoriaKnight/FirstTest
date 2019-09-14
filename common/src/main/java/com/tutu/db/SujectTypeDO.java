package com.tutu.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/14 15:59
 *     desc   : 题型
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "subject_type")
@EqualsAndHashCode(callSuper = false)
public class SujectTypeDO implements Serializable {

    private Long id;
    private Integer status;
    private String detail;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private Integer type;
}
