package com.yewen.entity;

import com.yewen.controller.ChatServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineMap {
    private static Map<String, ChatServerSocket> socketMap;
    public OnlineMap(){
        socketMap = new ConcurrentHashMap<String, ChatServerSocket>();
    }
    public static Map<String, ChatServerSocket> getSocketMap() {
        return socketMap;
    }

    public static void setSocketMap(Map<String, ChatServerSocket> socketMap) {
        OnlineMap.socketMap = socketMap;
    }
}
