package spf.websocket.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import spf.websocket.beans.Message;
import spf.websocket.beans.SendVo;
import spf.websocket.config.Constants;
import spf.websocket.service.MessageService;
import spf.websocket.utils.JsonUtil;

/**
 * 功能说明：系统日志WebSocket处理器
 * 可以继承 TextWebSocketHandler/BinaryWebSocketHandler}，
 * 或者简单的实现WebSocketHandler接口
 */
public class SystemWebSocketHandler implements WebSocketHandler {
	 
	private static final  Logger LOG = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	
	@Autowired
	private MessageService messageService;
    @Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOG.info("ConnectionEstablished:::sessionID==" + session.getId());
		String userName = (String) session.getAttributes().get(Constants.HTTP_SESSION_NANE);
		String sessionId = (String) session.getAttributes().get(Constants.HTTP_SESSION_ID);
		if (StringUtils.isNotEmpty(userName)&&StringUtils.isNotEmpty(sessionId) ) {
			WebSocketSessionUtils.add(userName, sessionId, session);
		}
		LOG.info("userName:::userName==" + userName);
		if (userName != null) {
			// 查询未读消息
			// int count = messageService.getUnReadNews(userName);
			List<SendVo> vo = messageService.queryUnReadMessage(userName);
			if(vo.size()>0){
				LOG.info("查询"+ userName+ "用户未读消息::"+ JsonUtil.toJsonString(vo));
				session.sendMessage(new TextMessage(JsonUtil.toJsonString(vo)));
				updateStatus(messageService.queryUnReadMessage(userName));
			}
			
		}

	}
 

	@Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	LOG.info("Received Message"+message.getPayload());
    	if(message.getPayloadLength()>0){
    		Message ms = JsonUtil.toBean(message.getPayload(), Message.class);
        	String userName = (String) session.getAttributes().get(Constants.HTTP_SESSION_NANE);  	
        	if(null != ms){
        		ms.setSenderId(userName);
        		ms.setSendTime(new Date());
        		sendMessageToUser(ms.getReceiverId(),ms);
        	}else{
        		session.sendMessage(new TextMessage("send message fail"));
        	}
    	}	
    }
 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOG.error(exception.getMessage(), exception);
        if (session.isOpen()) {
			session.close();
		}
		String userName = (String) session.getAttributes().get(Constants.HTTP_SESSION_NANE);
		String sessionId = (String) session.getAttributes().get(Constants.HTTP_SESSION_ID);
		WebSocketSessionUtils.remove(userName, sessionId);
    }
 
    @Override
	public void afterConnectionClosed(WebSocketSession session,CloseStatus closeStatus) throws Exception {
		LOG.debug("websocket connection closed......");
		String userName = (String) session.getAttributes().get(Constants.HTTP_SESSION_NANE);
		String sessionId = (String) session.getAttributes().get(Constants.HTTP_SESSION_ID);
		WebSocketSessionUtils.remove(userName, sessionId);
	}
 
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
	/**
	 * 给某个用户发送消息
	 * 
	 * @param userName
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageToUser(String userName, Message message)
			throws IOException {
		List<WebSocketSession> sessions = WebSocketSessionUtils.get(userName);
		if(!CollectionUtils.isEmpty(sessions)){
			for(WebSocketSession session: sessions ){
				if (session != null && session.isOpen()) {
					message.setReceiveTime(new Date());	
					message.setIsRead(1);
					session.sendMessage(new TextMessage(JsonUtil.toJsonString(message)));						
				}
			}
		}
		messageService.saveMessage(message);
	}
	
	private void updateStatus(List<SendVo> vos){
		List<Long> ids =  new ArrayList<Long>();
		for(SendVo vo : vos){
			for(Message message : vo.getMessageList()){
				ids.add(message.getId());
			}			
		}
		messageService.updateMessageStatusByID(ids);
	}

 
}