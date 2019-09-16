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
 *     time   : 2018/8/12 12:11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "sys_relation")
@EqualsAndHashCode(callSuper = false)
public class SysRelationDO implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;
    /**
     * 菜单id
     */
    private Long menuid;
    /**
     * 角色id
     */
    private Integer roleid;
}
