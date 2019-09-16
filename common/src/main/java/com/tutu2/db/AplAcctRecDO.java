package com.tutu2.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "apl_acct_rec")
@EqualsAndHashCode(callSuper = false)
public class AplAcctRecDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId; // 随机生成订单id
    private Long userId;
    private Integer type; // 类型，1：充值；2：支出
    private String receipt; // 票据
    private Integer receiptStatus; // 票据验证结果 0：未验证；1：验证成功；2：验证失败
    private String receiptVerifyRst; // 票据验证结果
    private String content; // 描述
    private Long textbookId; // 购买课程使用的书，查询icon使用
    private Long price; // 价格
    private Integer status;// 状态 0：无效；1：有效
    private Date createTime;
    private Date modifyTime;
}
