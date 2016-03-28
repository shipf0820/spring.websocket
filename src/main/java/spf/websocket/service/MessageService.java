package spf.websocket.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spf.websocket.beans.Message;
import spf.websocket.beans.SendVo;
import spf.websocket.mybitis.MessageMapper;


@Service
public class MessageService{
	
	static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
    @Autowired
    private MessageMapper messageMapper;

    public void saveMessage(Message message){
    	messageMapper.insertMessage(message);
    }
    
    public List<SendVo> queryUnReadMessage(String receiverId){
    	return messageMapper.selectUnReadMessageByUserId(receiverId);
    }
    
    public void updateMessageStatusByID(List<Long> ids){
    	messageMapper.updateMessagesStatusByIds(ids);
    }

    

}
