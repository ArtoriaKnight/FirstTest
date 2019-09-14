package com.tutu.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "task_course_user")
@EqualsAndHashCode(callSuper = false)
public class TaskCourseUserDO implements Serializable {
    @Id
    private Long id;
    private Long userId;
    private Integer taskCourseId;
    private Long textbookId;
    private Integer canLock;
    private Integer boxIsOpen;
    private Date createAt;
    private Date updateAt;

}
