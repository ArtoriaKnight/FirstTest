package com.tutu.db;

import java.io.Serializable;
import java.util.List;


@SuppressWarnings("serial")
public class HomePosition implements Serializable{
	
	/** course： 精品课    part_list：Part列表*/
	public enum ItemType {
		course,part_list;
	}

	/**ID*/
	private String id;
	/**模块标题*/
	private String title;
	/**简介*/
	private String summary;
	/**类型*/
	private String itemType;
	/**图片 course下配置*/
	private String icon;
	/**元素ID*/
	private String itemId;
	/**排序*/
	private Integer sort;
	/**状态 0下架   1上架*/
	private Integer state;
	/**子集元素*/
	private List<HomePositionElement> homePositionElement;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public List<HomePositionElement> getHomePositionElement() {
		return homePositionElement;
	}
	public void setHomePositionElement(List<HomePositionElement> homePositionElement) {
		this.homePositionElement = homePositionElement;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HomePosition [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", summary=");
		builder.append(summary);
		builder.append(", itemType=");
		builder.append(itemType);
		builder.append(", icon=");
		builder.append(icon);
		builder.append(", itemId=");
		builder.append(itemId);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", state=");
		builder.append(state);
		builder.append(", homePositionElement=");
		builder.append(homePositionElement);
		builder.append("]");
		return builder.toString();
	}
	
	
}
