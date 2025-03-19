package websocket;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

import java.util.List;
import java.util.Map;

import static utils.SharedConsts.CLIENT_ID_KEY_NAME;

public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> parameters = request.getParameterMap();
        if (parameters.containsKey(CLIENT_ID_KEY_NAME)) {
            String uuid = parameters.get(CLIENT_ID_KEY_NAME).get(0);
            config.getUserProperties().put(CLIENT_ID_KEY_NAME, uuid);
        }
    }
}
