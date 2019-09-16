package com.tutu2.db;

import java.io.Serializable;
import java.time.LocalDateTime;


@SuppressWarnings("serial")
public class TestRecord implements Serializable{

	private String userId;
	private String testData;
	private LocalDateTime testDate;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTestData() {
		return testData;
	}
	public void setTestData(String testData) {
		this.testData = testData;
	}
	public LocalDateTime getTestDate() {
		return testDate;
	}
	public void setTestDate(LocalDateTime testDate) {
		this.testDate = testDate;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestRecord [userId=");
		builder.append(userId);
		builder.append(", testData=");
		builder.append(testData);
		builder.append(", testDate=");
		builder.append(testDate);
		builder.append("]");
		return builder.toString();
	}
	
}
