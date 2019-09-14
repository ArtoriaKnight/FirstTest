package com.tutu.db;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "home_entry")
@EqualsAndHashCode(callSuper = false)
public class HomeEntryDO implements Serializable {
    @Id
    private Long    id 			  ;
    private String  title 		  ;
    private String  picUrl 	      ;
    private String  link 		  ;
    private String  showCondition ;
    private Integer itemType	  ;
    private Integer sort 		  ;
    private Date    createAt 	  ;
    private Date    updateAt 	  ;
    private Integer state 		  ;
    private Long    operatorId    ;

}
