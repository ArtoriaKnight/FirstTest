package com.tutu.db;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户标签
 */
@Data
@Table(name = "user_tags")
public class UserTagsDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;
    private Integer tagId;
    private Integer schoolAreaId = 0;
    private Date createTime;
    private Date modifyTime;

}
