package spring.websocket.repository.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import spf.websocket.beans.Message;
import spf.websocket.beans.SendVo;
import spf.websocket.mybitis.MessageMapper;
import spf.websocket.utils.JsonUtil;

public class MessageMapperTest extends BaseTest{
	
	@Autowired
	private MessageMapper messageDto;
	
	@Test
	public void selectUnreadMessageByUserIdTest(){
		List<SendVo> vo = messageDto.selectUnReadMessageByUserId("100000");
		System.out.println(JsonUtil.toString(vo));
	}
	
	@Test
	public void selectUnreadCountByUserIdTest(){
		int vo = messageDto.selectUnReadCountByUserId("100000");
		System.out.println(vo);
	}
	
	@Test
	public void updateMessagesStatusTest(){
		//messageDto.updateMessagesStatus(1);
	}
	@Test
	public void insertTest(){
		Message message = new Message();
		message.setSenderId("200000");
		message.setReceiverId("200000");
		message.setIsRead(0);
		message.setContent("testtest");
		messageDto.insertMessage(message);
		System.out.println(message.getId());
	    
	}
	

}
