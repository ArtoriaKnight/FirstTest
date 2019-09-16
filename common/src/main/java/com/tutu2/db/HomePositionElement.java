package com.tutu2.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HomePositionElement implements Serializable {	
	/**非库表字段*/
	private String id;
	private String icon;
	private String textBookName;
	private String grade;
	private long buyNum = 0;
	private Integer useCondition = 0;
	private Integer canLock = 2;
	private Integer sort;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTextBookName() {
		return textBookName;
	}
	public void setTextBookName(String textBookName) {
		this.textBookName = textBookName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public long getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(long buyNum) {
		this.buyNum = buyNum;
	}
	public Integer getUseCondition() {
		return useCondition;
	}
	public void setUseCondition(Integer useCondition) {
		this.useCondition = useCondition;
	}
	public Integer getCanLock() {
		return canLock;
	}
	public void setCanLock(Integer canLock) {
		this.canLock = canLock;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HomePositionElement [id=");
		builder.append(id);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", textBookName=");
		builder.append(textBookName);
		builder.append(", grade=");
		builder.append(grade);
		builder.append(", buyNum=");
		builder.append(buyNum);
		builder.append(", useCondition=");
		builder.append(useCondition);
		builder.append(", canLock=");
		builder.append(canLock);
		builder.append(", sort=");
		builder.append(sort);
		builder.append("]");
		return builder.toString();
	}
	
}
