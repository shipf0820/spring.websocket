package spf.websocket.beans;

import java.io.Serializable;
import java.util.List;


public class SendVo implements Serializable{
	

	private static final long serialVersionUID = -1402823813019323525L;

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
