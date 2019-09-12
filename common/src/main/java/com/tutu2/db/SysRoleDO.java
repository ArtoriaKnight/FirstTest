package com.tutu2.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/12 12:12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "sys_role")
@EqualsAndHashCode(callSuper = false)
public class SysRoleDO implements Serializable {
    @Id
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
}
