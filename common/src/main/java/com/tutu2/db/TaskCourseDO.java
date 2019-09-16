package com.tutu2.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "task_course")
@EqualsAndHashCode(callSuper = false)
public class TaskCourseDO implements Serializable{
    @Id
    private Long    id 			    ;
    private Long    taskId 			;
    private String  lockPicUrl      ;
    private String  title 			;
    private Integer type 			;
    private String  lockLink 		;
    private String  textbookIds 	;
    private String  rule 	        ;
    private String  gift         	;
    private String  giftLink 		;
    private Integer sort 			;
    private Date    createdAt 		;
    private Date    updatedAt 		;
    private Integer state 			;
    private Long    operatorId 		;
}
