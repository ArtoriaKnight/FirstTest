package com.tutu.db.app;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/21 23:36
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "feed_back")
@EqualsAndHashCode(callSuper = false)
public class FeedBackDO implements Serializable {
    @Id
    private Long id;
    private Long userId;
    private String icon;
    private String content;
    private Date createdAt;
}
