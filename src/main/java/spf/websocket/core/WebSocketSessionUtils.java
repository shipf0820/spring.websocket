package spf.websocket.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketSession;

import spf.websocket.config.Constants;

 
/**
 * seesion工具类
 */
public class WebSocketSessionUtils {
    
    //webSocket-session连接session管理
    private static Map<String, List<WebSocketSession>> wsClients = new ConcurrentHashMap<String, List<WebSocketSession>>();
    
 
    /**
     * 保存一个连接
     * @param sessionId
     * @param session
     */
    public static void add(String userName,String sessionId, WebSocketSession session){
    	List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
		if (CollectionUtils.isEmpty(wsClients.get(userName))) {
			sessionList.add(session);
		} else {
			boolean flag = true;
			sessionList = wsClients.get(userName);
			for (WebSocketSession wsSession : sessionList) {
				if ((String) wsSession.getAttributes().get(
						Constants.HTTP_SESSION_ID) == sessionId) {
					flag = false;
					break;
				}
			}
			if (flag) {
				sessionList.add(session);
			}
		}
		wsClients.put(userName, sessionList);
    }
 
    /**
     * 获取一个连接
     * @param sessionId
     * @return
     */
    public static WebSocketSession get(String userName,String sessionId){
    	List<WebSocketSession> sessionList = wsClients.get(userName);
    	if(null != sessionList && sessionList.size()>0){
    		for(WebSocketSession wsSession : sessionList){
    			String wsSessionId = (String) wsSession.getAttributes().get(Constants.HTTP_SESSION_ID);
    			if ( sessionId.equals(wsSessionId)) {
					return wsSession;
				}
    		}
    	}
    	return null;
    }
    
    /**
     * 获取一个用户的所有连接
     * @param sessionId
     * @return
     */
    public static List<WebSocketSession> get(String userName){
    	return wsClients.get(userName);
    }
 
    /**
     * 移除一个连接
     * @param sessionId
     */
    public static void remove(String userName,String sessionId) throws IOException {

    	List<WebSocketSession> sessionList = wsClients.get(userName);
    	if(null != sessionList && sessionList.size()>0){
    		for(WebSocketSession wsSession : sessionList){
    			String wsSessionId = (String) wsSession.getAttributes().get(Constants.HTTP_SESSION_ID);
    			if ( sessionId.equals(wsSessionId)) {
    				wsClients.get(userName).remove(wsSession);
    				break;
				}
    		}
    	}
    	if(CollectionUtils.isEmpty(wsClients.get(userName))){
    		wsClients.remove(userName);
    	}
       
    }

   
}
