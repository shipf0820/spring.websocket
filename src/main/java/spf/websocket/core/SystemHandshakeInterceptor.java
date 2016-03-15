package spf.websocket.core;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import spf.websocket.config.Constants;

/**
 * 功能说明：websocket连接的拦截器
 * 有两种方式
 * 一种是实现接口HandshakeInterceptor，实现beforeHandshake和afterHandshake函数
 * 一种是继承HttpSessionHandshakeInterceptor，重载beforeHandshake和afterHandshake函数
 */
public class SystemHandshakeInterceptor extends HttpSessionHandshakeInterceptor{
	 
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		
		if (serverHttpRequest instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
			HttpSession session = servletRequest.getServletRequest()
					.getSession(false);
			if (session != null) {
				// 使用userName区分WebSocketHandler，以便定向发送消息
				String userName = (String) session
						.getAttribute(Constants.HTTP_SESSION_NANE);
				attributes.put(Constants.HTTP_SESSION_NANE, userName);
				attributes.put(Constants.HTTP_SESSION_ID, session.getId());
			}
		}
		return true;
    }
 
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
	
	

}
