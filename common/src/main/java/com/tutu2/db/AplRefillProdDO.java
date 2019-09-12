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
@Table(name = "apl_refill_prod")
@EqualsAndHashCode(callSuper = false)
public class AplRefillProdDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*** 1：充值 2：VIP */
    private Integer type;
    private String name;
    private Long price;
    private String levelId;
    private Date createTime;
    private Date modifyTime;
}
