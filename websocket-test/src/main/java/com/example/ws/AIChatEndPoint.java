package com.example.ws;

import com.example.utils.AIChat;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ServerEndpoint(value = "/")
@Component
public class AIChatEndPoint {
    @OnOpen
    public void onOpen() {
        System.out.println("Opened");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received: " + message);

        // 启动新线程处理AI回复，避免阻塞WebSocket消息处理
        new Thread(() -> {
            try {
                // 调用AIChat工具类获取流式回复
                AIChat.getAIReply(message, session);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    session.getBasicRemote().sendText("Error: " + e.getMessage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }

    @OnClose
    public void onClose() {
        System.out.println("Closed");
    }
}
