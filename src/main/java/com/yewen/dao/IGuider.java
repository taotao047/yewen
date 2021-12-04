package com.yewen.dao;

import com.yewen.entity.Guider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IGuider {
    @Select("select * from guiders where guidermajor like '${guiderMajor}'")
    List<Guider> selectGuiders(Guider guider);
    @Select("select * from guiders")
    List<Guider> selectAll();
}
