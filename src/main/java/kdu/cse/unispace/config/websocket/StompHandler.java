package kdu.cse.unispace.config.websocket;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("WebSocket 연결 요청: 세션 ID = {}", accessor.getSessionId());
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            log.info("WebSocket 연결 종료: 세션 ID = {}", accessor.getSessionId());
        }

        return message;
    }
}
