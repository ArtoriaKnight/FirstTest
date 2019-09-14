package com.tutu.db;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class StudyCenterSoucer implements Serializable{

	private String id;
	private Long userId;
	private Long textBookId;
	private Long unitId;
	private Long partId;
	private Integer state;
	private LocalDateTime createDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTextBookId() {
		return textBookId;
	}
	public void setTextBookId(Long textBookId) {
		this.textBookId = textBookId;
	}
	public Long getUnitId() {
		return unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public Long getPartId() {
		return partId;
	}
	public void setPartId(Long partId) {
		this.partId = partId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StudyCenter [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", textBookId=");
		builder.append(textBookId);
		builder.append(", unitId=");
		builder.append(unitId);
		builder.append(", partId=");
		builder.append(partId);
		builder.append(", state=");
		builder.append(state);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append("]");
		return builder.toString();
	}
	
	
}
