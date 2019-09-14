package com.tutu.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "subject_dialogue")
@EqualsAndHashCode(callSuper = false)
public class SubjectDialogueDO implements Serializable {
    @Id
    private Long id            ;
    private Long subjectOneId;
    private String avatar        ;
    private String text          ;
    private String icon          ;
    private String audio         ;
    private Integer roleId       ;
    private Integer creatorId    ;
    private Date createTime   ;
    private Date updateTime   ;
}
