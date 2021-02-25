package cn.com.agree.aweb.configuration;

import cn.com.agree.aweb.ws.AdminWebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new AdminWebSocketHandler(), "/ws/msg")
                .setAllowedOrigins("*")
                .withSockJS()
        ;
    }


}
