package com.yewen.dao;

import com.yewen.entity.Visitor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IVisitor {
    /*登陆用户查找*/
    @Select("select * from " +
            "visitors where " +
            "uid='${uID}' and upassword='${uPassword}'")
    List<Visitor> loginCheck(Visitor visitor);
    /*注册检查*/
    @Select("select * from visitors where uid='${uID}' OR utel = '${uTel}' OR uemail='${uEmail}'")
    List<Visitor> registerCheck(Visitor visitor);
    /*保存*/
    @Insert("insert into visitors values('${uID}','${uPassword}','${uTel}','${uEmail}')")
    void saveIntoVisitors(Visitor visitor);
}
