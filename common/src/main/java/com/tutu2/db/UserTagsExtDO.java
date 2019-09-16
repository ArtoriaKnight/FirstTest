package com.tutu2.db;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户标签
 */
@Data
public class UserTagsExtDO extends UserTagsDO implements Serializable {
    private String content;
    private String title;
}
