package com.yewen.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

public class C3P0Source extends UnpooledDataSourceFactory {
    public C3P0Source(){
        this.dataSource = new ComboPooledDataSource();
    }
}
