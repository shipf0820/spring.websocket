package spf.websocket.beans;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import spf.websocket.utils.JackJsonDateTimeFormat;
import spf.websocket.utils.JackJsonDateTimeParse;

public class Message implements Serializable{
	
	private static final long serialVersionUID = -5483814183205886945L;
	
	private Long id;
	private String senderId;
	private String receiverId;
	private String content;
	private int isRead;

	@JsonDeserialize(using = JackJsonDateTimeParse.class)
	@JsonSerialize(using = JackJsonDateTimeFormat.class)
	private Date sendTime;
	
	@JsonDeserialize(using = JackJsonDateTimeParse.class)
	@JsonSerialize(using = JackJsonDateTimeFormat.class)
	private Date receiveTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}



	
}
