package com.tutu2.db;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
@Table(name = "coin_record")
public class CoinRecord implements Serializable{
	
	@Id
	private String id;
	private String userId;
	private Double amount;
	private String targetType;
	private String targetId;
	private LocalDateTime date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CoinRecord [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", targetType=");
		builder.append(targetType);
		builder.append(", targetId=");
		builder.append(targetId);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
	
}
