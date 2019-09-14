package com.tutu.db;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户使用的客户端信息记录 user_client_info
 */
@Data
public class UserClientInfoExtDO extends UserClientInfoDO implements Serializable {
    private String username;
}
