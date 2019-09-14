package com.tutu.db;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/25 19:12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "share_record")
@ApiModel("分享记录")
@EqualsAndHashCode(callSuper = false)
public class ShareRecord implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private Long partsId;

    private Date expireAt;
    private Date createdAt;
}
