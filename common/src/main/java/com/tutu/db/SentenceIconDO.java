package com.tutu.db;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/12/18 21:35
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@Data
@Table(name = "sentence_icon")
public class SentenceIconDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long subjectOneId;

    private String sceneGraph;
}
