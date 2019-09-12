package com.tutu2.db;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "advertisement")
@SuppressWarnings("serial")
public class Advertisement implements Serializable{
	
	public enum PositionType {
		home_last,user_center
	}
	
	@Id
	private String id;
	/**图片地址*/
	private String showPictureUrl;
	/**链接地址*/
	private String linkItem;
	/**扩展信息*/
	private Object extendMess;
	/**广告位置*/
	private String positionType;
	/**1 开启 2关闭*/
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
	public String getLinkItem() {
		return linkItem;
	}
	public void setLinkItem(String linkItem) {
		this.linkItem = linkItem;
	}
	public Object getExtendMess() {
		return extendMess;
	}
	public void setExtendMess(Object extendMess) {
		this.extendMess = extendMess;
	}
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
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
		builder.append("Advertisement [id=");
		builder.append(id);
		builder.append(", showPictureUrl=");
		builder.append(showPictureUrl);
		builder.append(", linkItem=");
		builder.append(linkItem);
		builder.append(", extendMess=");
		builder.append(extendMess);
		builder.append(", positionType=");
		builder.append(positionType);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}
	
}
