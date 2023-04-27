/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/25 21:29 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@Data @TableName("t_book")
public class Book {
    //@TableId
    //@TableId与@TableId(value = "id",type = IdType.ASSIGN_ID)效果相同，都是雪花算法

    @TableId(value = "id",type = IdType.ASSIGN_ID)

    private Long id;
    private String name;
    private  Double price;
    private  String author;
    private LocalDate pdate;
}
