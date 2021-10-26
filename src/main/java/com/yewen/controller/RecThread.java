package com.yewen.controller;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RecThread implements Runnable{
    private HttpSession session;
    HttpServletResponse response = null;
    DatagramSocket socket = null;
    public RecThread(HttpServletResponse response, HttpSession session){
        this.response = response;
        this.session = session;
    }
    @Override
    public void run() {
        try {
            socket = new DatagramSocket(6666);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        System.out.println("接收端开启成功!");
        byte[] msg = new byte[1024];
        System.out.println("进入循环");
        while (true){
            DatagramPacket packet = new DatagramPacket(msg, 0, msg.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String string = new String(packet.getData());
            System.out.println("接收到的数据:"+string);
            session.setAttribute("datas",string);
            System.out.println(session.getAttribute("datas"));
            socket.disconnect();
        }

    }
}
