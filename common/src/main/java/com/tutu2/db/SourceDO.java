package com.tutu2.db;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/14 21:56
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
@Data
@Table(name = "source")
@EqualsAndHashCode(callSuper = false)
public class SourceDO implements Serializable {
    @Id
    @Range(min = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ApiModelProperty(name = "text",value = "素材文本")
    private String text;
    @ApiModelProperty(name = "icon",value = "图标地址url")
    private String icon;
    @ApiModelProperty(name = "audio",value = "音频地址url")
    private String audio;
    @ApiModelProperty(name = "explainsArray",value = "多次释义,数组形式")
    private String explainsArray;
    @ApiModelProperty(name = "phonetic",value = "音标")
    private String phonetic;
    @ApiModelProperty(name = "translation",value = "单词释义,数组形式")
    private String translation;
    @ApiModelProperty(name = "textbookId",value = "教材id")
    private Long textbookId;
    @ApiModelProperty(name = "createdAt",value = "创建时间")
    private Date createdAt;
}
