package com.tutu2.db;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SourceItem implements Serializable{
	
	public enum ItemType {
		AUDIO,ICON
	}
	
	private String id;
	private String sourceId;
	private String itemMess;
	private String itemType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getItemMess() {
		return itemMess;
	}
	public void setItemMess(String itemMess) {
		this.itemMess = itemMess;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SourceItem [id=");
		builder.append(id);
		builder.append(", sourceId=");
		builder.append(sourceId);
		builder.append(", itemMess=");
		builder.append(itemMess);
		builder.append(", itemType=");
		builder.append(itemType);
		builder.append("]");
		return builder.toString();
	}
	
}
