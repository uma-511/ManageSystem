package com.warrior.common.push;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.warrior.common.cache.PushCache;
import com.warrior.common.web.WarriorSession;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Map;

@Log
public class PushService {

    private static SocketIOServer server;

    public static void startSevice(){
        try {
            Configuration config = new Configuration();
            config.setHostname("localhost");
            config.setPort(9092);
            config.setMaxFramePayloadLength(1024*1024);
            config.setMaxHttpContentLength(1024*1024);
            server = new SocketIOServer(config);

            server.addConnectListener(new ConnectListener() {
                @Override
                public void onConnect(SocketIOClient client) {
                    String uuid = client.getSessionId().toString();
                    String token = client.getHandshakeData().getSingleUrlParam("token");
                    if(WarriorSession.getItem(token) != null){
                        PushCache.addClient(uuid,token,client);
                    }
                }
            });

            server.addDisconnectListener(new DisconnectListener() {
                @Override
                public void onDisconnect(SocketIOClient client) {
                    String sa = client.getRemoteAddress().toString();
                    log.info("disconnect: "+sa);
                    //获取客户端IP
                    String clientIp = sa.substring(1,sa.indexOf(":"));
                    String uuid = client.getSessionId().toString();
                    PushCache.removeClient(uuid);

                }
            });

            server.addEventListener(EventType.NOTICE_INFO.getValue(), String.class, new DataListener<String>() {
                @Override
                public void onData(SocketIOClient client, String data, AckRequest ackRequest) throws Exception {
                    String sa = client.getRemoteAddress().toString();
                    log.info("Notice_info: "+sa);
                    //获取客户端IP
                    String clientIp = sa.substring(1,sa.indexOf(":"));
                    //获取客户端url参数
                    Map params = client.getHandshakeData().getUrlParams();

                }
            });
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopServer(){
        if(server != null){
            server.stop();
            server = null;
            PushCache.removeAll();
        }
    }

    /**
     * 给所有连接客户端发送消息
     * @param eventType
     * @param message
     */
    public static void sendMessageToAllClient(EventType eventType,String message){
        Collection<SocketIOClient> clients = server.getAllClients();
        for (SocketIOClient client : clients){
            client.sendEvent(eventType.getValue(),message);
        }
    }

    /**
     * 给单个客户端发送消息
     * @param token
     * @param eventType
     * @param message
     */
    public static void sendMessageToOneClient(String token,EventType eventType,String message){
        if (!StringUtils.isEmpty(token)){
            SocketIOClient client = PushCache.getClient(token);
            if (client != null){
                client.sendEvent(eventType.getValue(),message);
            }
        }
    }
}