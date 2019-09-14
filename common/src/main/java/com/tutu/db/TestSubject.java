package com.tutu.db;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <pre>
 *     @author : pillar
 *     e-mail : 347637454@qq.com
 *     time   : 2018/8/15 15:58
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@SuppressWarnings("serial")
public class TestSubject extends SubjectOneDO implements Serializable {
	
    @ApiModelProperty(name = "subjectScore",value = "题目分值说明")
    private String subjectScore;

	public String getSubjectScore() {
		return subjectScore;
	}
	public void setSubjectScore(String subjectScore) {
		this.subjectScore = subjectScore;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestSubject [subjectScore=");
		builder.append(subjectScore);
		builder.append("]");
		return builder.toString();
	}
	
}
