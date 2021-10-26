package com.yewen.controller;

import com.yewen.dao.IVisitor;
import com.yewen.entity.Visitor;
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
import java.net.URLDecoder;
import java.util.List;

@WebServlet("/register")
/*
* 处理注册请求，首先判断用户名、电话号码、邮箱有无重复
* 若无重复，则向前端发送可注册指令
*
*
* */
public class RegisterServlet extends HttpServlet {
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
        resp.setContentType("text/html;charset=UTF-8");
        String uID= URLDecoder.decode(req.getParameter("uID"),"UTF-8");
        String uPassword =URLDecoder.decode(req.getParameter("uPassword"),"UTF-8");
        String uEmail=URLDecoder.decode(req.getParameter("uEmail"),"UTF-8");
        String uPhone= URLDecoder.decode(req.getParameter("uPhone"),"UTF-8");
        sqlSession = sqlSessionFactory.openSession();
        iVisitor = sqlSession.getMapper(IVisitor.class);
        Visitor visitor = new Visitor();
        visitor.setUID(uID);
        visitor.setUPassword(uPassword);
        visitor.setUTel(uPhone);
        visitor.setUEmail(uEmail);
        List<Visitor> list=iVisitor.registerCheck(visitor);
        System.out.println("list:"+list);
        if(list.isEmpty()){//用户可以注册
            System.out.println(visitor);
            iVisitor.saveIntoVisitors(visitor);
            sqlSession.commit();
            resp.getWriter().write("true");
        }else {
            resp.getWriter().write("false");
        }
    }
}
