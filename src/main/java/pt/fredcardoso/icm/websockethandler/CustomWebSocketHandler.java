package pt.fredcardoso.icm.websockethandler;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		String clientMessage = message.getPayload();
		int[] arr = new int[] { 0, 100, 200, 300, 400, 500, 1000, 600, 4000, 6000, 300, 1200 };

		Random rand = new Random();

		for (int i = 0; i < arr.length; i++) {
			int randomIndexToSwap = rand.nextInt(arr.length);
			int temp = arr[randomIndexToSwap];
			arr[randomIndexToSwap] = arr[i];
			arr[i] = temp;
		}

		if (clientMessage.startsWith("hello") || clientMessage.startsWith("greet")) {
			session.sendMessage(new TextMessage("Hello there!"));
		} else if (clientMessage.startsWith("test")) {
			session.sendMessage(new TextMessage(Arrays.toString(arr)));
		} else if (clientMessage.startsWith("time")) {
			LocalTime currentTime = LocalTime.now();
			session.sendMessage(new TextMessage(currentTime.toString()));
		} else {
			session.sendMessage(new TextMessage("Unknown command"));
		}
	}
}
