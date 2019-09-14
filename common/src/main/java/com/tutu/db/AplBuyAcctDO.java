package com.tutu.db;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 苹果内购账户
 */
@Data
@Table(name = "apl_buy_acct")
public class AplBuyAcctDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long balance;
    private Long totalRefill;
    private Long totalPay;
    private Date createTime;
    private Date modifyTime;
}
