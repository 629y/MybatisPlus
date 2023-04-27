/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.mp;

import cn.zxy.mp.mapper.DbMapper;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import java.util.List;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/24 17:50 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
public class Demo {
    public static void main(String[] args) {
        TransactionFactory tx = new JdbcTransactionFactory();
        PooledDataSource ds = new PooledDataSource();
        ds.setDriver("com.mysql.cj.jdbc.Driver");
        ds.setPassword("root");
        ds.setUrl("jdbc:mysql:/db");
        ds.setUsername("root");
        Environment env = new Environment("dev",tx,ds);
        Configuration cfg = new Configuration(env);
        cfg.addMappers("cn.zxy.mp.mapper");
        //cfg.addMapper(DbMapper.class);
        SqlSessionFactory sf = new MybatisSqlSessionFactoryBuilder().build(cfg);
        SqlSession ss = sf.openSession();
        DbMapper dm = ss.getMapper(DbMapper.class);
        System.out.println(dm.dbs());

        System.out.println(dm.tbs());

        List<String> dss = ss.selectList("dbs");
        System.out.println(dss);
        List<String> ts = ss.selectList("cn.zxy.mp.mapper.DbMapper.tbs");
        System.out.println(ts);
    }
}
