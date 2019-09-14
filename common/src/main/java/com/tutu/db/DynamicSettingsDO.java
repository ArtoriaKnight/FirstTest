package com.tutu.db;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 一些动态配置
 */
@Data
@Table(name = "dynamic_settings")
public class DynamicSettingsDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*** 配置类型：
     * 1：微信小程序使用；
        2：后台使用；
        3：用户标签；
        4：渠道信息；
        5：临时信息；
        6：校区信息 */
    private Integer type;
    /*** 配置标题 */
    private String title;
    /*** 配置内容 */
    private String content;
    /*** 0：在用；1：作废 */
    private Integer state;
    @Column(updatable = false)
    private Date createTime;
    @Column(updatable = true)
    private Date updateTime;
    private Long createUserId;
    private Long updateUserId;
}
