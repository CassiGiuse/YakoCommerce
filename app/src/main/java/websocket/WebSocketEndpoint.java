package websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static utils.SharedConsts.CLIENT_ID_KEY_NAME;

@ServerEndpoint(value = "/ws", configurator = WebSocketConfigurator.class)
public class WebSocketEndpoint {

    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        String clientID = (String) config.getUserProperties().get(CLIENT_ID_KEY_NAME);

        if (clientID != null) {
            sessions.put(clientID, session);
        } else {
            try {
                final String reasonMessage = String.format("%s non fornito", CLIENT_ID_KEY_NAME);
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, reasonMessage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String clientID = null;
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            if (entry.getValue().equals(session)) {
                clientID = entry.getKey();
                break;
            }
        }

        if (clientID != null) {
            sendMessageToClient(clientID, "Hai inviato: " + message);
        } else {
            System.out.println("ClientID non trovato per la sessione corrente.");
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.values().remove(session);
        System.out.println("Sessione chiusa.");
    }

    public static void sendMessageToClient(String clientID, String message) {
        Session session = sessions.get(clientID);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastMessage(String message) {
        for (Session session : sessions.values()) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
