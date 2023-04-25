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
