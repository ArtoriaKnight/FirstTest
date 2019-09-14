package com.tutu.db;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 弹窗广告对象
 */
@Data
@Table(name = "ad_popups")
public class ADPopupsDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer type; // 弹窗类型：1：首页弹窗；2：VIP弹窗
    private String img; // 图片
    private String link; // 跳转链接
    private Integer rule; // 弹出条件(condition是mysql/druid关键字？？)：1：新用户；2：未开通体验课
    private Integer times; // 弹出次数：1：仅弹出一次
    private Integer state; // 0：有效；1：无效
    private Integer sort; // 排序
    private Date createTime;
    private Date updateTime;
}
