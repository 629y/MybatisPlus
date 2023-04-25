/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.mp;

import cn.zxy.mapper.DbMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/25 9:40 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@SpringJUnitConfig(locations = "classpath:appconfig.xml") @Slf4j
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
                log.info("hello world");
        }

        @Test
        void m(){
                for (String n : ctx.getBeanDefinitionNames()) {
                        System.out.println(n);
                }
        }

        @Autowired
        SqlSession ss;

        @Test
        void m2(){
                List<String> ds  = ss.selectList("dbs");
                log.info("{}",ds);
        }
}
