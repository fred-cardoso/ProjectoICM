package pt.fredcardoso.icm.websockethandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.services.BidService;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

	// List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	ConcurrentHashMap<Integer, ArrayList<WebSocketSession>> sessions = new ConcurrentHashMap<Integer, ArrayList<WebSocketSession>>();

	@Autowired
	private BidService bidService;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		afterConnectionEstablished(session, message);

		int productId = Integer.parseInt(message.getPayload());

		List<Bid> bids = bidService.getBidsFromProduct(productId);

		Collections.sort(bids, new Comparator<Bid>() {

			public int compare(Bid b1, Bid b2) {
				return b1.getDatetime().compareTo(b2.getDatetime());
			}
		});

		int size = bids.size();

		if (bids.size() > 20) {
			size = 20;
		}

		HashMap<String, Float> teste = new HashMap<String, Float>();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");

		float[] values = new float[size];

		for (int i = 0; i < size; i++) {
			String time = sf.format(bids.get(i).getDatetime());
			float value = bids.get(i).getValue();

			teste.put(time, value);

			values[i] = bids.get(i).getValue();
		}

		String[] labels = new String[size];

		for (int i = 0; i < size; i++) {
			labels[i] = sf.format(bids.get(i).getDatetime());
		}

		JsonObject toSend = new JsonObject();

		for (int i = 0; i < size; i++) {
			toSend.add(labels[i], new JsonPrimitive(values[i]));
		}

		for (WebSocketSession webSocketSession : sessions.get(productId)) {
			if (webSocketSession.isOpen()) {
				webSocketSession.sendMessage(new TextMessage(toSend.toString()));
			}
		}
	}

	public void afterConnectionEstablished(WebSocketSession session, TextMessage message) throws Exception {
		// the messages will be broadcasted to all users.
		int productId = -1;

		try {
			productId = Integer.parseInt(message.getPayload());
		} catch (Exception e) {

		}

		if (this.sessions.get(productId) != null) {

			ArrayList<WebSocketSession> sessions = (ArrayList<WebSocketSession>) this.sessions.get(productId);
			sessions.add(session);
			this.sessions.put(productId, sessions);

		} else {

			ArrayList<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
			sessions.add(session);
			this.sessions.put(productId, sessions);

		}
	}
}
