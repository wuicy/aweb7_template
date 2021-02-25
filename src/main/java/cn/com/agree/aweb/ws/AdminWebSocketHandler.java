package cn.com.agree.aweb.ws;

import cn.com.agree.aweb.entity.DefaultUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yesx
 * @date 2020/4/24
 */
//@Component
@Slf4j
public class AdminWebSocketHandler extends TextWebSocketHandler {

    private static final int maximumPoolSize = 100;
    private static final ConcurrentHashMap<WebSocketSession, DefaultUserDetails> wsMap = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (wsMap.size() < maximumPoolSize ) {
            if(! wsMap.containsKey(session) ){
                UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) session.getPrincipal();
                DefaultUserDetails user = (DefaultUserDetails) authentication.getPrincipal();
                wsMap.put(session, user);
                log.info("websocket[{}] connected. exists count:{}", session.getId(), wsMap.size());
            }
        } else {
            log.warn("thread pool is full");
            TextMessage textMessage = new TextMessage("thread pool is full".getBytes());
            session.sendMessage(textMessage);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if(wsMap.containsKey(session)){
            wsMap.remove(session);
            log.info("websocket[{}] disconnected. exists count:{}", session.getId(), wsMap.size());
        }

    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        DefaultUserDetails user = wsMap.get(session);
        if(user != null){
            if(user.getAuthorities().stream().anyMatch(item -> "ROLE_ADMIN".equals(item.getAuthority()))){
                log.info("管理员发送消息:{}", message.getPayload());
                wsMap.forEach((ws, po) -> {
                    try {
                        if(!session.getId().equals(ws.getId())){
                            ws.sendMessage(message);
                        }
                    } catch (IOException e) {
                        log.error("发送ws消息失败", e);
                    }
                });
            }
        }
    }
}
