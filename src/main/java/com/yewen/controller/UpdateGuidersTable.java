package com.yewen.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yewen.dao.IGuider;
import com.yewen.dao.IVisitor;
import com.yewen.entity.Guider;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/update")
public class UpdateGuidersTable extends HttpServlet {
    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private IGuider iGuider;
    static {
        try {
            /*mybatis初始化*/
            inputStream = Resources.getResourceAsStream("MybatisConfig.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*处理客户端需要更新导生表的请求*/
    /*输入：专业*/
    /*输出：导生相关信息*/
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*是否根据major检索*/
        String isByMajor = URLDecoder.decode(req.getParameter("isByMajor"),"UTF-8");
        String guiderMajor = URLDecoder.decode(req.getParameter("guiderMajor"),"UTF-8");
        /*目前这个根据guiderName来实现还没实现*/
        String guiderName = URLDecoder.decode(req.getParameter("guiderName"),"UTF-8");
        Guider guider = new Guider();
        guider.setGuiderMajor(guiderMajor);
        if (isByMajor.equals("True")){
            sqlSession = sqlSessionFactory.openSession();
            iGuider = sqlSession.getMapper(IGuider.class);
            List<Guider> guiders;
            /*查询所有导生*/
            if (guiderMajor.equals("ALL")){
                guiders=iGuider.selectAll();
            }else {
                guiders = iGuider.selectGuiders(guider);
            }
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write(TransToJSONString(guiders));
        }else {
            /*根据导生名字来检索*/
        }
    }
    String TransToJSONString(List<Guider> guiders){
        /*导生列表需求数据*/
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",0);
        jsonObject.put("msg","");
        jsonObject.put("count",1000);
        ArrayList<JSONObject> datas = new ArrayList<>();
        JSONObject dataJson = new JSONObject();
        for (Guider item:
                guiders) {
            dataJson.put("guiderPro",item.getGuiderMajor());
            dataJson.put("guiderName",item.getGuiderName());
            dataJson.put("state",item.getState());
            datas.add((JSONObject) dataJson.clone());
        }
        jsonObject.put("data",datas);
        return jsonObject.toJSONString();
    }
}
