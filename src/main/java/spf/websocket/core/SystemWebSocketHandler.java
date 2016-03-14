package spf.websocket.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public static final Map<String, WebSocketSession> userSocketSessionMap = new HashMap<String, WebSocketSession>();
	
	@Autowired
	private MessageService messageService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	 LOG.info("ConnectionEstablished:::sessionID=="+session.getId());
    	 String userName = (String) session.getAttributes().get(Constants.WEBSOCKET_SESSION_NANE);
    	 if (StringUtils.isNotEmpty(userName) && userSocketSessionMap.get(userName) == null) {
 			userSocketSessionMap.put(userName, session);
 		 }
         
         LOG.info("userName:::userName=="+userName);
         if(userName!= null){
             //查询未读消息
             //int count = messageService.getUnReadNews(userName);
        	 LOG.info("查询"+userName+"用户未读消息::"+JsonUtil.toJsonString(messageService.queryUnReadMessage(userName)));
             session.sendMessage(new TextMessage(JsonUtil.toJsonString(messageService.queryUnReadMessage(userName)))); 
             updateStatus(messageService.queryUnReadMessage(userName));
         }
    	
    }
 

	@Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	LOG.info("Received Message"+message.getPayload());
    	Message ms = JsonUtil.toBean(message.getPayload(), Message.class);
    	String userName = (String) session.getAttributes().get(Constants.WEBSOCKET_SESSION_NANE);  	
    	if(null != ms){
    		ms.setSenderId(userName);
    		ms.setSendTime(new Date());
    		sendMessageToUser(ms.getReceiverId(),ms);
    	}else{
    		session.sendMessage(new TextMessage("send message fail"));
    	}
		
    }
 
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOG.error(exception.getMessage(), exception);
        if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				LOG.info("Socket会话已经移除:用户名" + entry.getKey());
				break;
			}
		}

    }
 
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	 LOG.debug("websocket connection closed......");
    	 Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap
 				.entrySet().iterator();
 		// 移除Socket会话
 		while (it.hasNext()) {
 			Entry<String, WebSocketSession> entry = it.next();
 			if (entry.getValue().getId().equals(session.getId())) {
 				userSocketSessionMap.remove(entry.getKey());
 				LOG.info("Socket会话已经移除:用户ID" + entry.getKey());
 				break;
 			}
 		}
    }
 
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
    
	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap
				.entrySet().iterator();

		// 多线程群发
		while (it.hasNext()) {
			final Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().isOpen()) {
				// entry.getValue().sendMessage(message);
				new Thread(new Runnable() {
					public void run() {
						try {
							if (entry.getValue().isOpen()) {
								entry.getValue().sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
			}

		}
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
		WebSocketSession session = userSocketSessionMap.get(userName);
		if (session != null && session.isOpen()) {
			message.setReceiveTime(new Date());		
			session.sendMessage(new TextMessage(JsonUtil.toJsonString(message)));
			message.setIsRead(1);
			messageService.saveMessage(message);
		}else{
			messageService.saveMessage(message);
		}
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