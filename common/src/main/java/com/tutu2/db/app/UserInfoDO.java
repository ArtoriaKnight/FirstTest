package com.tutu2.db.app;

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
 *     time   : 2018/8/16 15:13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "user_info")
@EqualsAndHashCode(callSuper = false)
public class UserInfoDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    /**
     * 练习教材id
     */
    private Long textbookIdPractice;
    /**
     * 配音教材id
     */
    private Long textbookIdAudio;
    private Long bookVersionId;
    private String realName;
    private String qqNumber;
    private String wechatNumber;
    private Integer sex;
    private Date birthday;
    private Date createdAt;
    private String email;
    private Integer userLevel;
    private Long userMoney;
    private Date exprieTime;
    private Date updatedAt;
    private String icon;
    private String mobile;
    private Integer channel;
    private Date vipStartTime;
    private Integer graded;
    private Integer channelId;
}
