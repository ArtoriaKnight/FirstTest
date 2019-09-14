package com.tutu.db;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户开口跟读次数记录
 */
@Data
@Table(name = "user_info_ext")
public class UserInfoExtDO implements Serializable {
    @Id
    private Long userId;
    private String weeklySpeaking; // 最近7天每一天的跟读次数 [{"day": "2019-05-16", "times": 123}, ......]
    private Integer totalSpeaking; // 历史总跟读次数
    @Column(insertable = true)
    private Date createTime;
    @Column(updatable = true)
    private Date updateTime;
}
