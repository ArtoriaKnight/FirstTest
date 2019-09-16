package com.tutu2.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HomeBannerDO implements Serializable{
	
	/**ID*/
	private String id;
	/**展示图链接地址*/
	private String showPictureUrl;
	/**元素类型*/
	private String itemType;
	/**自定义信息体*/
	private String itemMess;
	/**开始时间*/
	private Long startDate = 0L;
	/**结束时间*/
	private Long endDate = 1649509018000L;
	/**状态  0：下架      1：上架*/
	private Integer state;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShowPictureUrl() {
		return showPictureUrl;
	}
	public void setShowPictureUrl(String showPictureUrl) {
		this.showPictureUrl = showPictureUrl;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemMess() {
		return itemMess;
	}
	public void setItemMess(String itemMess) {
		this.itemMess = itemMess;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HomeBannerDO [id=");
		builder.append(id);
		builder.append(", showPictureUrl=");
		builder.append(showPictureUrl);
		builder.append(", itemType=");
		builder.append(itemType);
		builder.append(", itemMess=");
		builder.append(itemMess);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}

	public enum BannerItemType {
		home_last,user_center,study_center;
	}

}
