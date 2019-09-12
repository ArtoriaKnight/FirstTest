package com.tutu2.db;

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
 *     time   : 2018/8/14 15:45
 *     desc   : 年级
 *     version: 1.0
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "grade")
public class GradeDO implements Serializable {

    @Id
    private Long id;
    private String gradeName;
    /**
     * 1正常2关闭
     */
    private Integer status;

    private Date createdAt;
}
