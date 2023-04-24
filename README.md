# Mybatis-Plus 框架入门教程

## 第一章 mybatis-plus简介

官方网址：https://baomidou.com

![image-20230424183441060](D:\新建文件夹\java\ssms-mybatis\MybatisPlus\README\image-20230424183441060.png)

## 第二章 入门项目实例

### 2.1 maven 项目直接使用

（1）建立项目，pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.zxy</groupId>
    <artifactId>MybatisPlus</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- com.baomidou/mybatis-plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.5.3.1</version>
        </dependency>


        <!-- com.mysql/mysql-connector-j -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>8.0.33</version>
        </dependency>

    </dependencies>
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
            </resource>
        </resources>
    </build>
</project>
```

(2)编写Mapper

`src/main/java/cn/zxy/mp/mapper/DbMapper.java`

```java
/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.mp.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>Description:</p>
 *
 * <p>Powered by zxy On 2023/4/24 17:54 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
public interface DbMapper {
    @Select("show databases")
    List<String> dbs();
    List<String> tbs();
}
```

`src/main/java/cn/zxy/mp/mapper/DbMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.zxy.mp.mapper.DbMapper">
    <select id="tbs" resultType="string">
        show tables
    </select>

</mapper>
```

(3)编写主程序测试

```java
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
```

![image-20230424191001738](D:\新建文件夹\java\ssms-mybatis\MybatisPlus\README\image-20230424191001738.png)















