/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.mapper;

import cn.zxy.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * <p>Description:</p>
 *
 * <p>Powered by zxy On 2023/4/25 21:28 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Select("select id,name,author from t_book")
    public List<Map<String,Object>> shows();

    public int count();
}
