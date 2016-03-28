package spf.websocket.beans;

import java.util.List;


public class SendVo {
	
	private String senderId;
	
	private List<Message> messageList;

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	


	
	
	
}
