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

### 2.2 spring 使用mp

`Ch02-SpringDemo`

(1)`pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>cn.zxy</groupId>
        <artifactId>MybatisPlus</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>Ch02-SpringDemo</artifactId>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!-- org.springframework/spring-context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.0.8</version>
        </dependency>

        <!-- org.springframework/spring-context -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>6.0.8</version>
        </dependency>

        <!-- org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.26</version>
            <scope>provided</scope>
        </dependency>

        <!-- org.springframework/spring-jdbc -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>6.0.8</version>
        </dependency>

        <!-- ch.qos.logback/logback-classic -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.7</version>
            <scope>test</scope>
        </dependency>

        <!-- com.baomidou/mybatis-plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.5.3.1</version>
        </dependency>

        <!-- mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>

        <!-- org.junit.jupiter/junit-jupiter-api -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.9.2</version>
            <scope>test</scope>
        </dependency>

        <!-- com.zaxxer/HikariCP -->
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
            <version>5.0.1</version>
        </dependency>

    </dependencies>
</project>
```

(2)`resources/db.properties`

```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql:/db
db.username=root
db.password=root
```

(3)`resources/appconfig.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
<!-- <import resource=""/>   -->
    <context:property-placeholder location="db.properties"/>
    <bean id="dd" class="java.util.Date"/>
<!--    数据库连接池-->
    <bean id="ds" class="com.zaxxer.hikari.HikariDataSource">
        <property name="driverClassName" value="${db.driver}"/>
        <property name="jdbcUrl" value="${db.url}"/>
        <property name="username" value="${db.username}"/>
        <property name="password" value="${db.password}"/>
    </bean>

    <bean id="sf" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <property name="dataSource" ref="ds"/>
        <property name="mapperLocations" value="mapper/*.xml"/>
    </bean>

<!--    <bean id="ss" class="org.mybatis.spring.SqlSessionFactoryBean">-->
<!--        <property name="dataSource" ref="ds"/>-->
<!--        <property name="mapperLocations" value="classpath*:mapper/DbMapper.xml"/>-->
<!--    </bean>-->

    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="cn.zxy.mapper"/>
    </bean>
</beans>
```

(4)编写接口`src/main/java/cn/zxy/mapper/DbMapper.java`

```java
/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Description:</p>
 *
 * <p>Powered by zxy On 2023/4/25 9:44 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@Repository
public interface DbMapper {
    public List<String> dbs();
}
```

配置文件`resources/mapper/DbMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.zxy.mapper.DbMapper">
    <select id="dbs" resultType="string">
        show databases
    </select>
</mapper>
```

(5)测试程序

`src/test/java/cn/zxy/mp/DemoSpringXml.java`

```java
/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.mp;

import cn.zxy.mapper.DbMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/25 9:40 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@SpringJUnitConfig(locations = "classpath:appconfig.xml")
public class DemoSpringXml {
        @Autowired
        ApplicationContext ctx;
        @Autowired
        DbMapper dm;
        @Test
        void m1() throws Exception {
                //SqlSession ss = sf.getObject().openSession();
                //System.out.println(ss);
                System.out.println(dm.dbs());
        }
        @Test
        void m(){
                for (String n : ctx.getBeanDefinitionNames()) {
                        System.out.println(n);
                }
        }
}
```

`m1()`

![image-20230425101859812](D:\新建文件夹\java\ssms-mybatis\MybatisPlus\README\image-20230425101859812.png)

`m()`

![image-20230425101754495](D:\新建文件夹\java\ssms-mybatis\MybatisPlus\README\image-20230425101754495.png)











