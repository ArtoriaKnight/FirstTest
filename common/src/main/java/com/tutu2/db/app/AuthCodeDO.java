package com.tutu2.db.app;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/16 14:25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "auth_code")
public class AuthCodeDO implements Serializable {
    @Id
    private Long id;
    private String mobile;
    private Integer code;
    private Integer type;
    private Integer status;
    private Long saveTime;
}
