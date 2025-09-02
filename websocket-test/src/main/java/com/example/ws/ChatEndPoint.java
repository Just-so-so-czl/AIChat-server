//package com.example.ws;
//
//import com.alibaba.fastjson2.JSON;
//import com.example.utils.MessageUtils;
//import jakarta.servlet.http.HttpSession;
//import jakarta.websocket.*;
//import jakarta.websocket.server.ServerEndpoint;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
////@ServerEndpoint(value = "/", configurator = GetHttpSessionConfig.class)
//@ServerEndpoint(value = "/")
//@Component
//public class ChatEndPoint {
//
//    private static final Map<String, Session> onlineUsers= new ConcurrentHashMap<>();
//
//    private HttpSession httpSession;
//
////    @OnOpen
////    public void onOpen(Session session, EndpointConfig config) {
////        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
////        String user = (String) httpSession.getAttribute("user");
////        onlineUsers.put(user,session);
////
////        MessageUtils.getMessage(true,null,getFriends());
////    }
////
////    @OnOpen
////    public void onOpen(Session session) {
////        System.out.println("onOpen");
////    }
////
////    private Set getFriends() {
////        Set<String> set = onlineUsers.keySet();
////        return set;
////    }
////
////    private void broadcastAllUsers(String message) {
////        try {
////            Set<Map.Entry<String, Session>> entries = onlineUsers.entrySet();
////            for (Map.Entry<String, Session> entry : entries) {
////                Session session = entry.getValue();
////                session.getBasicRemote().sendText(message);
////            }
////        } catch (IOException ex) {
////                ex.printStackTrace();
////        }
////    }
////
////    @OnMessage
////    public void onMessage(String message) {
////        Message msg = JSON.parseObject(message, Message.class);
////
////        String toName = msg.getToName();
////        String mess = msg.getMessage();
////
////        Session session = onlineUsers.get(toName);
////        String user = (String) this.httpSession.getAttribute("user");
////        String msg1 = MessageUtils.getMessage(false, user, mess);
////        try {
////            session.getBasicRemote().sendText(msg1);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
////
////    @OnClose
////    public void onClose() {
////        String user = (String) httpSession.getAttribute("user");
////        onlineUsers.remove(user);
////        String message = MessageUtils.getMessage(true, null, getFriends());
////        broadcastAllUsers(message);
////    }
//}
