package com.tutu.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "apl_buy_vip_rec")
@EqualsAndHashCode(callSuper = false)
public class AplBuyVIPRecDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String orderId;
    private String receipt; // 票据
    private Integer receiptStatus; // 票据验证结果 0：未验证；1：验证成功；2：验证失败
    private String receiptVerifyRst; // 票据验证结果
    private Integer type; // type：2：VIP；3：Activity
    private Integer prodLevelId; // apl_refill_prod -> id
    @Column(insertable = true)
    private Date createTime;
    @Column(updatable = true)
    private Date modifyTime;
}
