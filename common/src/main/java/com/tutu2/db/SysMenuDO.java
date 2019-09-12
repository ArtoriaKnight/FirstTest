package com.tutu2.db;

import com.tutu2.annotation.*;
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
 *     time   : 2018/8/12 12:50
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_menu")
public class SysMenuDO implements Serializable {
    /**
     * 主键id
     */
    @Id
    @TreeNodeId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 地址
     */
    private String url;
    /**
     * 名称
     */
    @TreeNodeLabel
    private String menuName;
    /**
     * 1启用2禁用
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     *  修改时间
     */
    private Date updatedAt;
    /**
     * 父级id
     */
    @TreeNodePid
    private Long parentId;
    /**
     * 菜单路径
     */
    @TreePath
    private String path;
    /**
     * 排序
     */
    private Integer sortValue;
    /**
     * 菜单图片
     */
    @TreeNodeIcon
    private String icon;
    /**
     * 菜单类型1左侧菜单2按钮3接口
     */
    private Integer menuScope;
}
