package com.yewen.controller;

import javax.servlet.http.HttpSession;

public class TestThread  implements Runnable{
    HttpSession session;
    public TestThread( HttpSession session){
        this.session = session;
    }
    @Override
    public void run() {
        session.setAttribute("datas","haha");
        System.out.println("设置成功");
    }
}
