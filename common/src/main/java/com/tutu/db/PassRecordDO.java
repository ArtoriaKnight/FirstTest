package com.tutu.db;

import io.swagger.annotations.ApiModel;
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
 *     time   : 2018/8/18 18:30
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@ApiModel("闯关记录")
@EqualsAndHashCode(callSuper = false)
@Table(name = "pass_record")
public class PassRecordDO implements Serializable {
    private Long userId;
    private Long textbookId;
    private Long unitsId;
    private Long partsId;
    private Long sessionId;
    private Long score;
    private Date createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
