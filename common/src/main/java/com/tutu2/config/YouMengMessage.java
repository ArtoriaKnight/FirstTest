package com.tutu2.config;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;

@SuppressWarnings("serial")
public class YouMengMessage implements Serializable{

	/**描述信息*/
	private String description;
	/**消息体*/
	private TransmissionTemp transmissionTemp;
	/**指定单个目标*/
	private String uid;
	/**指定目标群体*/
	private CopyOnWriteArraySet<String> uidSet;
	
	public class TransmissionTemp implements Serializable{
		
		/**简介*/
		private String summary;
		/**文本*/
		private String content;
		/**元素*/
		private String toItem;
		/**元素ID*/
		private String toItemId;

		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getToItem() {
			return toItem;
		}
		public void setToItem(String toItem) {
			this.toItem = toItem;
		}
		public String getToItemId() {
			return toItemId;
		}
		public void setToItemId(String toItemId) {
			this.toItemId = toItemId;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("TransmissionTemp [summary=");
			builder.append(summary);
			builder.append(", content=");
			builder.append(content);
			builder.append(", toItem=");
			builder.append(toItem);
			builder.append(", toItemId=");
			builder.append(toItemId);
			builder.append("]");
			return builder.toString();
		}	
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public TransmissionTemp getTransmissionTemp() {
		return transmissionTemp;
	}
	public void setTransmissionTemp(TransmissionTemp transmissionTemp) {
		this.transmissionTemp = transmissionTemp;
	}
	public CopyOnWriteArraySet<String> getUidSet() {
		return uidSet;
	}
	public void setUidSet(CopyOnWriteArraySet<String> uidSet) {
		this.uidSet = uidSet;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("YouMengMessage [description=");
		builder.append(description);
		builder.append(", uid=");
		builder.append(uid);
		builder.append(", transmissionTemp=");
		builder.append(transmissionTemp);
		builder.append(", uidSet=");
		builder.append(uidSet);
		builder.append("]");
		return builder.toString();
	}
	
}
