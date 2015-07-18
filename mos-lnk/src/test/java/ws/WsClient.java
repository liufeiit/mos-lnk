package ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2015年7月19日 上午1:48:11
 */
@ClientEndpoint
public class WsClient {
	private static CountDownLatch latch;

	private Logger logger = Logger.getLogger(getClass());
    
	@OnOpen
	public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@OnMessage
	public String onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            logger.info("Received ...." + message);
            String userInput = bufferRead.readLine();
            return userInput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
		latch.countDown();
	}

	public static void main(String[] args) {
		latch = new CountDownLatch(1);
		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(WsClient.class, new URI("ws://localhost:8025/websockets/game"));
			
			latch.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
