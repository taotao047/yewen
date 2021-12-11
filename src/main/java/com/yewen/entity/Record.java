package com.yewen.entity;

import com.yewen.dao.IMessage;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class Record {

    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;//数据库链接
    private IMessage iMessage;//数据库查询接口
    static {
        try {
            inputStream = Resources.getResourceAsStream("MybatisConfig.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            System.out.println("导入配置成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RecordMessage(String senderID, String recevierID,String message){
        sqlSession = sqlSessionFactory.openSession();
        iMessage = sqlSession.getMapper(IMessage.class);
        Message messages = new Message();
        messages.setSender(senderID);
        messages.setRecevier(recevierID);
        messages.setMessage(message);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messages.setTime(format.format(System.currentTimeMillis()));
        System.out.println(messages);
        iMessage.saveIntoMessageRecords(messages);
        sqlSession.commit();
    }

    public List<Message> getMessageRecords(String VisitorID1, String VisitorID2){
        sqlSession = sqlSessionFactory.openSession();
        iMessage = sqlSession.getMapper(IMessage.class);
        List<Message> list = iMessage.checkRecords(VisitorID1,VisitorID2);
        return list;
    }
}
