package com.tutu2.db;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/11/14 22:34
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "session_customer_pass")
public class SessionCustomerPassDO implements Serializable {

    @ApiModelProperty(name = "id",value = "关系id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(name = "sessionId",value = "小关卡Id")
    private Long sessionId;
    @ApiModelProperty(name = "customsPassId",value = "小关卡Id")
    private Long customsPassId;
    @ApiModelProperty(name = "textbookId",value = "教材Id")
    private Long textbookId;
    @ApiModelProperty(name = "sort",value = "排序字段，升序")
    private Integer sort;
}
