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

#### 2.2.1 logback日志框架使用

首先，`pom.xml`添加依赖

```xml
 <!-- ch.qos.logback/logback-classic -->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.7</version>
        </dependency>
```

其次，classpath目录`resources/logback.xml`

```xml
<?xml version="1.0"?>
<configuration>
    <!-- ch.qos.logback.core.ConsoleAppender 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n -->
            <pattern>[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 日志级别 -->
    <logger name="cn" level="DEBUG"/>

    <root level="OFF">
        <appender-ref ref="STDOUT" />
    </root>
    <logger name="ch" level="off" />
</configuration>
```

`logback-test.xml`

```xml
<?xml version="1.0"?>
<configuration>
    <!-- ch.qos.logback.core.ConsoleAppender 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <!-- 日志级别 -->
    <root level="OFF">
        <appender-ref ref="STDOUT" />
    </root>
    <logger name="cn" level="DEBUG"/>
    <!--<logger name="ch.qos.logback" level="off"/>-->


</configuration>
```

#### 2.2.2 xml配置SqlSession

```
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
    <constructor-arg index="0" ref="sqlSessionFactory" />
</bean>
@Autowired
SqlSession ss;

@Test
void m2(){
    List<String> ds  = ss.selectList("dbs");
    log.info("{}",ds);
}
```

#### 2.2.3 关闭mp banner信息

```
<bean id="config" class="com.baomidou.mybatisplus.core.config.GlobalConfig">
    <property name="banner" value="false"/>
</bean>

<bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="mapperLocations" value="classpath*:mapper/*.xml"/>
    <property name="globalConfig" ref="config"/>
</bean>
```

#### 2.2.4 spring 配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">


    <context:component-scan base-package="cn.webrx"/>

    <!-- <import resource=""/> -->

    <context:property-placeholder location="classpath:db.properties"/>

    <bean id="dd" class="java.util.Date"/>

    <!-- 数据库连接池-HikariDataSource -->
    <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource" destroy-method="close">
        <property name="driverClassName" value="${db.driver}"/>
        <property name="jdbcUrl" value="${db.url}"/>
        <property name="username" value="${db.username}"/>
        <property name="password" value="${db.password}"/>
    </bean>

    <bean id="ddl" class="cn.webrx.mp.MysqlDdl"/>

    <bean id="config" class="com.baomidou.mybatisplus.core.config.GlobalConfig">
        <property name="banner" value="false"/>
    </bean>

    <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="mapperLocations" value="classpath*:mapper/*.xml"/>
        <property name="globalConfig" ref="config"/>
    </bean>

    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="cn.webrx.mapper"/>
    </bean>

    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sqlSessionFactory" />
    </bean>

    <!-- 引入其它配置文件 -->
    <!-- <import resource="classpath:db.xml"/> -->
</beans>
/*
 * Copyright (c) 2006, 2023, webrx.cn All rights reserved.
 *
 */
package cn.webrx.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * <p></p>
 * <p>Powered by webrx On 2023-04-25 11:21:16</p>
 *
 * @author webrx [webrx@126.com]
 * @version 1.0
 * @since 17
 */
@Configuration
@MapperScan("cn.webrx.mapper")
@ComponentScan("cn.webrx")
//@Import(MybaitsConfig.class)
@PropertySource("classpath:db.properties")
public class AppConfig {
    @Value("${db.driver}")
    private String driver;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    @Bean(name = "ds", destroyMethod = "close")
    public HikariDataSource hikariDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setPassword(password);
        ds.setUsername(username);
        ds.setJdbcUrl(url);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean(name = "sf")
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSource ds) throws IOException {
        MybatisSqlSessionFactoryBean sf = new MybatisSqlSessionFactoryBean();
        sf.setDataSource(ds);

        GlobalConfig cc = new GlobalConfig();
        cc.setBanner(false);
        sf.setGlobalConfig(cc);

        PathMatchingResourcePatternResolver path = new PathMatchingResourcePatternResolver();
        sf.setMapperLocations(path.getResources("classpath:mapper/*.xml"));
        return sf;
    }

    @Bean
    public SqlSession sqlSession(SqlSessionFactory sf) {
        SqlSessionTemplate ss = new SqlSessionTemplate(sf);
        return ss;
    }


}
```

测试

```
/*
 * Copyright (c) 2006, 2023, webrx.cn All rights reserved.
 *
 */
package cn.webrx.mp;

import cn.webrx.config.AppConfig;
import cn.webrx.mapper.DbMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * <p></p>
 * <p>Powered by webrx On 2023-04-25 11:25:53</p>
 *  @SpringJUnitConfig(locations = "classpath:appconfig.xml") 配置文件xml
 *  @SpringJUnitConfig(AppConfig.class) 编程java
 * @author webrx [webrx@126.com]
 * @version 1.0
 * @since 17
 */
@SpringJUnitConfig(AppConfig.class)
public class Demo {
    @Autowired
    ApplicationContext ctx;

    @Autowired
    SqlSession ss;

    @Autowired
    DbMapper dm;

    @Test
    void m2(){
        System.out.println(ss.selectList("dbs"));

        System.out.println(dm.dbs());
    }

    @Test
    void m1(){

        for (String beanDefinitionName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
```

### 2.3 springboot

> 2.x 2.7.11 logback spring 5 java8 tomcat 9
>
> 3.x 3.0.6 log4j2 spring 6 java17 tomcat 10

#### 2.3.1 建立项目

`Ch03-SpringBootDemo`

springboot 2.7.11

`pom.xml`

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

    <artifactId>Ch03-SpringBootDemo</artifactId>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!-- org.springframework.boot/spring-boot-dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>3.0.6</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <version>3.0.6</version>
            <!-- 排除loggin -->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <version>3.0.6</version>
        </dependency>

        <!--com.baomidou/mybatis-plus-boot-starter -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.3.1</version>
        </dependency>

        <!-- mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
            <version>1.18.24</version>
        </dependency>

        <!--org.springframework.boot/spring-boot-starter-log4j2 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
            <version>3.0.6</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.7.11</version>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

`src/main/resources/application.yml`

```yml
spring:
  datasource:
    url: jdbc:mysql:/db
    username: root
    password: root
    type: com.zaxxer.hikari.HikariDataSource

  main:
    banner-mode: console


mybatis-plus:
  global-config:
    banner: on
    enable-sql-runner: true
  mapper-locations: classpath:/mapper/**/*.xml

logging:
  level:
    root: off
    cn.zxy: debug
```

#### 2.3.2 入口程序

`src/main/java/cn/zxy/App.java`

```java
/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy;

import com.baomidou.mybatisplus.core.MybatisPlusVersion;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/25 14:20 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@SpringBootApplication
@MapperScan("cn.zxy.mapper")
public class App implements ApplicationRunner {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(SpringBootVersion.getVersion());
        System.out.println(MybatisPlusVersion.getVersion());
    }
}
```

![image-20230425192429267](D:\新建文件夹\java\ssms-mybatis\MybatisPlus\README\image-20230425192429267.png)

数据库初始文件

`src/main/resources/db/tag-tag-schema.sql`

```mysql
drop database if exists `mpdb`;
create database `mpdb` default character set utf8;
use `mpdb`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COMMENT '标签名字',
  `type` int(11) NULL DEFAULT NULL COMMENT '所属类别：0文章，1类别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '标签';
```

`src/main/resources/db/tag-data.sql`

```
use `mpdb`;
INSERT INTO `tag` VALUES (1, 'Java', 0);
INSERT INTO `tag` VALUES (2, 'JVM', 0);
INSERT INTO `tag` VALUES (3, 'Golang', 1);
```

#### 2.3.3 单元测试

`src/test/java/cn/zxy/SpringBootDemo.java`

```java
/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/25 15:01 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */

@SpringBootTest(classes = App.class) @Log4j2
public class SpringBootDemo {
    @Autowired
    ApplicationContext ctx;

    @Test
    void mm(){
        System.out.println("hello world");
        log.info("ininininin - info");
    }
}
```

![image-20230425195858582](D:\新建文件夹\java\ssms-mybatis\MybatisPlus\README\image-20230425195858582.png)

#### 2.3.4 springboot 初始化sql

application.yml

```yml
spring:
  main:
    banner-mode: off
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:mysql:/db
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

  sql:
    init:
      encoding: UTF-8
      separator: ;
      username: root
      password: root
      #这两个脚本文件放在resources/db/db.sql
      schema-locations: classpath*:db/db.sql
      data-locations: classpath*:db/data.sql
      mode: always

logging:
  level:
    root: off


mybatis-plus:
  global-config:
    banner: off
```

db.sql脚本文件

```sql
drop database if exists `mdb`;
create database `mdb` default character set utf8;
use `mdb`;
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);
```

data.sql脚本

```
use `mdb`;
insert into `user` (id, name, age, email) VALUES (1, 'Jone', 18, 'test1@baomidou.com'), (2, 'Jack', 20, 'test2@baomidou.com'), (3, 'Tom', 28, 'test3@baomidou.com'), (4, 'Sandy', 21, 'test4@baomidou.com'), (7, '赵强', 25, 'webrx@126.com'), (5, 'Billie', 24, 'test5@baomidou.com');
```

## 第三章 CRUD

```
@SpringBootApplication
@MapperScan("cn.webrx.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 3.1 如何编写mapper文件

#### 接口 BookMapper.java

App.java入口程序同级包下，在源码添加一个@Mapper就可以了，一般要继承Mapper接口

```
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}

//如果Mapper接口没有注解，也可以在入口程序类上添加 @MapperScan("cn.webrx.mapper")
```

#### 接口映射 BookMapper.xml

默认放在`src/main/resources/mapper/BookMapper.xml`

![image-20230425164056785](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAAAAACIM/FCAAACh0lEQVR4Ae3ch5W0OgyG4dt/mQJ2xgQPzJoM1m3AbALrxzrf28FzsoP0HykJEEAAAUQTBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEkKK0789+GK/I2ezfQB522PnS1qc8pGgXvr4tE4aY0XOUWlGImThWgyCk6DleixzE7qwBkg/MGiDPlVVAyp1VQGrPKiACDhFI6VkF5LmzCki+sg7IwDoglnVAil0IMkeG9CyUiwsxLFUVFzJJOQaKCjFCDN9RXMjIX7W6ztZXZDKKCyn8sWJvH+nca7WHDN9lROlAliPH9iRKCPI4cswFJQWxB46toLQgQ9jhn5QYZA9DOkoMUoQde5YapAxDWkoNYsOQR3KQd9CxUnIQF4S49CB9ENKlBxmDEKsFUgMCCCCAAHIrSF61f6153Ajy8nyiPr8L5MXnmm4CyT2fzN4DUvHZ+ntA2tOQBRBAAAEEEEAAAQQQ7ZBaC6TwSiDUaYHQ2yuB0MN+ft+43whyrs4rgVCjBUKTFshLC6TUAjGA3AxSaYFYLZBOC2RUAsk8h5qTg9QcbEoOsoQhQ2qQhsO5xCD5dgB5JQaZ+KBKGtKecvR81Ic0ZDjByKdDx0rSEDZ/djQbH+bkIdvfJFm98BfV8hD2zprfVdlu9PxVeyYAkciREohRAplJCaRSAplJCcQogTjSAdlyHRBvSAekJR0QRzogA+mADJkOiCPSAPEtqYBshlRAXC43hxix2QiOuEZkVERykGyNo9idIZKE0HO7XrG6OiMShlDWjstVzdPgXtUH9v0CEidAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQAABBBBAAAEEEEAAAQQQQP4HgjZxTpdEii0AAAAASUVORK5CYII=)

### 3.2 insert

#### 3.2.1 dao mapper insert

```
/*
 * Copyright (c) 2006, 2023, webrx.cn All rights reserved.
 *
 */
package cn.webrx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p></p>
 * <p>Powered by webrx On 2023-04-25 16:24:56</p>
 *
 * @author webrx [webrx@126.com]
 * @version 1.0
 * @since 17
 */
@Data
@TableName("t_book")
public class Book {
    @TableId
    //@TableId(value = "id",type = IdType.ASSIGN_ID)
    //@TableId(value = "id",type = IdType.AUTO) AUTO 代表忽略主键，让数据库自己维护（自增）
    //@TableId(value = "id",type = IdType.INPUT) 要求输入主键值
    //@TableId(value = "id",type = IdType.ASSIGN_UUID) uuid取除- 32位UUID字符串，char(32) primary key
    private Long id;
    private String name;
    private Double price;
    private String author;
    private LocalDate pdate;
}
@Test
void m13(){
    Book book = new Book();
    book.setName("《mp开发入门》");
    book.setPrice(50d);
    book.setAuthor("李四");
    book.setPdate(LocalDate.now());
    //bm.insert(book);

    //aaa396923d722e423bc467c54d59c051
    System.out.println("aaa396923d722e423bc467c54d59c051".length());
}
```

#### 3.2.2 service save

CRUD 接口使用service方式 实现插入数据

（1）写接口

```
cn.webrx.service.BookService.java
public interface BookService extends IService<Book> {
}
```

（2）接口实现类

```
cn.webrx.service.BookServiceImpl.java
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService  {

}

//也可以直接编写实现类,这样可以不写service,直接编写业务实现类
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IService<Book> {
}
```

（3）可以使用，单元测试

```
@Autowired
BookService bs;

@Test
void m13(){
    Book book = new Book();
    book.setName("《mp开发入门》");
    book.setPrice(50d);
    book.setAuthor("李四");
    book.setPdate(LocalDate.now());
    bs.save(book);
}
```

### 3.3 select

> 条件构造器
>
> QueryWrapper(LambdaQueryWrapper) 和 UpdateWrapper(LambdaUpdateWrapper) 的父类 用于生成 sql 的 where 条件, entity 属性也用于生成 sql 的 where 条件,注意: entity 生成的 where 条件与 使用各个 api 生成的 where 条件**没有任何关联行为**

```
@Test
void m15() {
    //System.out.println(bm.selectList(null));
    QueryWrapper<Book> wrapper = new QueryWrapper<>();
    //wrapper.like(true,"name","java");
    //wrapper.like(false,"name","mp");
    //wrapper.likeLeft(false,"name","mp");
    //wrapper.likeRight("name","mp");
    //wrapper.likeRight(true,"name","mp");

    //"author"是数据表的列名
    //wrapper.likeRight("author","李");
    wrapper.gt(true, "price", 10);

    System.out.println(bm.selectList(wrapper));
}
```

### 3.4 delete

### 3.5 update





