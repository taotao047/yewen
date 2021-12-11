package com.yewen.dao;

import com.yewen.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IMessage {

    @Select("Select * from MessageRecords where (sender = '${V1}'and recevier = '${V2}') or (sender = '${V2}'and recevier = '${V1}')")
    List<Message> checkRecords(@Param("V1") String Visitor1, @Param("V2") String Visitor2);

    @Insert("insert into MessageRecords values('${sender}','${recevier}','${message}','${time}')")
    void saveIntoMessageRecords(Message message);
}
