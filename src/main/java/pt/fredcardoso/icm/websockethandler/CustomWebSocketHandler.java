package pt.fredcardoso.icm.websockethandler;

import java.time.LocalTime;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
	
	@Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {

        String clientMessage = message.getPayload();

        if (clientMessage.startsWith("hello") || clientMessage.startsWith("greet")) {
            session.sendMessage(new TextMessage("Hello there!"));
        } else if (clientMessage.startsWith("time")) {
            LocalTime currentTime = LocalTime.now();
            session.sendMessage(new TextMessage(currentTime.toString()));
        } else {

            session.sendMessage(new TextMessage("Unknown command"));
        }
    }
}
