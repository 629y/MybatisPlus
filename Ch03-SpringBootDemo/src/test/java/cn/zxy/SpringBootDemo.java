/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy;
import java.time.LocalDate;
import java.time.LocalDateTime;

import cn.zxy.entity.Book;
import cn.zxy.mapper.BookMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.List;

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

    @Autowired
    BookMapper bm;

    @Test
    void m13(){
        Book book = new Book();
        book.setName("《mp开发入门》");
        book.setPrice(50D);
        book.setAuthor("李四");
        book.setPdate(LocalDate.now());
        bm.insert(book);
    }
    @Test
    void m12(){
        List<Book> books = bm.selectList(null);
        System.out.println(books);

        Book b = bm.selectById(1);
        System.out.println(b);

        System.out.println(bm.shows());

        System.out.println(bm.count());
    }
    @Test
    void mm(){
        System.out.println("hello world");
        log.info("ininininin - info");
    }
}