package com.tutu.db;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user_repeat_score")
public class UserRepeatScoreDO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long subjectOneId;
    private Long dialogueId;
    private int score;
    private String audioUrl;
    private Date createTime;
    private Date updateTime;
    private Long passId;
}
