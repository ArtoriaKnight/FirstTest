package com.tutu.db;

import java.io.Serializable;


@SuppressWarnings("serial")
public class QuestionCenter implements Serializable{
	
	/**父标题*/
	private String parentTitle;
	/**子标题*/
	private String sonTitle;
	/**回答*/
	private String answer;
	public String getParentTitle() {
		return parentTitle;
	}
	public void setParentTitle(String parentTitle) {
		this.parentTitle = parentTitle;
	}
	public String getSonTitle() {
		return sonTitle;
	}
	public void setSonTitle(String sonTitle) {
		this.sonTitle = sonTitle;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuestionCenter [parentTitle=");
		builder.append(parentTitle);
		builder.append(", sonTitle=");
		builder.append(sonTitle);
		builder.append(", answer=");
		builder.append(answer);
		builder.append("]");
		return builder.toString();
	}
	
}
