package com.yewen.controller;

import com.alibaba.fastjson.JSONObject;
import com.yewen.dao.IVisitor;
import com.yewen.entity.Visitor;
import lombok.Data;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private IVisitor iVisitor;
    static {
        try {
            inputStream = Resources.getResourceAsStream("MybatisConfig.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            System.out.println("导入配置成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //基础设置
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //接受来自客户端的uid和密码，然后在数据库中判断是否存在此id和密码的一行
        String uID = req.getParameter("uID");
        String uPassword = req.getParameter("uPassword");
        System.out.println(uID);
        System.out.println(uPassword);
        sqlSession = sqlSessionFactory.openSession();
        iVisitor = sqlSession.getMapper(IVisitor.class);
        Visitor visitor = new Visitor();
        visitor.setUID(uID);
        visitor.setUPassword(uPassword);
        List<Visitor> visitors = iVisitor.loginCheck(visitor);
        if (visitors.isEmpty()) {//用户名或者密码错误
            resp.getWriter().write("false");
        } else {//登陆成功
            //转到主页面
            resp.getWriter().write("true");
            req.getSession().setAttribute("uID", visitors.get(0).getUID());
            req.getSession().setAttribute("uPassword", visitors.get(0).getUPassword());
            req.getSession().setAttribute("uEmail", visitors.get(0).getUEmail());
            req.getSession().setAttribute("uTel", visitors.get(0).getUTel());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
