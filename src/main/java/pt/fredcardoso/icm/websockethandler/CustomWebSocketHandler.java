package pt.fredcardoso.icm.websockethandler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import pt.fredcardoso.icm.model.Bid;
import pt.fredcardoso.icm.services.BidService;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private BidService bidService;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		int productId = Integer.parseInt(message.getPayload());
		
		List<Bid> bids = bidService.getBidsFromProduct(productId);
		
		Collections.sort(bids, new Comparator<Bid>() {

	        public int compare(Bid b1, Bid b2) {
	            return b1.getDatetime().compareTo(b2.getDatetime());
	        }
	    });
		
		int size = bids.size();
		
		if(bids.size() > 20) {
			size = 20;
		}
		
		HashMap<String, Float> teste = new HashMap<String, Float>();
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
		
		float[] values = new float[size];
		
		for(int i = 0; i < size; i++) {
			String time = sf.format(bids.get(i).getDatetime());
			float value = bids.get(i).getValue();
			
			teste.put(time, value);
			
			values[i] = bids.get(i).getValue();
		}
		
		String[] labels = new String[size];
		
		for(int i = 0; i < size; i++) {	
			labels[i] = sf.format(bids.get(i).getDatetime());
		}
		
		JsonObject toSend = new JsonObject();
		
		for (int i = 0; i < size; i++) {
			toSend.add(labels[i], new JsonPrimitive(values[i]));
		}
		
		session.sendMessage(new TextMessage(toSend.toString()));
		
		/*String clientMessage = message.getPayload();
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
		}*/
	}
}
