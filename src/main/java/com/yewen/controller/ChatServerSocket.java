package com.yewen.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yewen.entity.OnlineMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleToIntFunction;

@ServerEndpoint("/chat-server/{userID}")
public class ChatServerSocket {
    //存取每个客户端向服务器发送请求的serversocket。
    private static Map<String, ChatServerSocket> socketMap = new ConcurrentHashMap<String, ChatServerSocket>();

    public OnlineMap onlineMap = new OnlineMap();
    //客户端与服务器之间的session
    private Session session;
    private String userID = null;
    private String targetUserID = null;

    @OnOpen
    public void onOpen(@PathParam("userID") String userID, Session session) {
        this.session = session;
        this.userID = userID;
        socketMap.put(userID, this);
        System.out.println(socketMap.toString());
    }

    @OnClose
    public void onClose(Session session) {
        socketMap.remove(userID);
        System.out.println(userID + "连接已关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自" + userID + "的信息:" + message);
        JSONObject jsonObject = JSON.parseObject(message);
        if (jsonObject.get("isSystem") == Boolean.TRUE) {
            //这里是学生向导生请求聊天，需要在导生页面上显示请求。
            if (jsonObject.get("GuidersResp") == Boolean.FALSE || jsonObject.get("GuidersResp") == null) {
                try {
                    //这里是学生向导生发起聊天请求，targetID 是导生的id，而userID是学生的id,询问导生是否同意聊天。
                    socketMap.get(jsonObject.getString("targetID")).session.
                            getBasicRemote().sendText("{\"isSystem\":" + true +",\"Request\":"+true+ ",\"targetID\":" + "\""+userID+"\"" + "}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {//是导生看到学生申请聊天后的回复信息，同意或者拒绝
                //向申请同该导生聊天的同学发送导生同意与否的信息。
                //这里导生回复以后说明已经是拒绝了，因为接受不会向服务端发送信息，而是直接开始聊天。
                if(jsonObject.get("Refuse")==Boolean.TRUE){//导生拒绝与该同学聊天
                    try {
                        socketMap.get(jsonObject.getString("targetID")).
                                session.getBasicRemote().sendText("{\"isSystem\":"+true+",\"GuidersResp\":"+true+",\"Refuse\":"+true+"}");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {//已经开始聊天了
            try {
                socketMap.get(jsonObject.getString("targetID")).session.getBasicRemote()
                        .sendText("{\"isSystem\":" + false + ",\"targetID\":" + targetUserID + ",\"message\": \"" + jsonObject.getString("message") + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}

