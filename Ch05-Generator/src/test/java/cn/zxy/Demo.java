/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy;

import java.util.Collections;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/27 9:11 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
public class Demo {
    public static void main(String[] args) {
//        FastAutoGenerator.create("jdbc:mysql:/erp","root","root")
//                .templateEngine(new FreemarkerTemplateEngine())
//                .execute();
        FastAutoGenerator.create("jdbc:mysql:/erp","root","root")
                .globalConfig(e->{
                    e.author("zxy").outputDir("d:\\mp\\Ch05-Generator\\src\\main\\java\\");
                })
                .packageConfig(e->{
                    e.parent("cn.zxy").moduleName("erp")
                            .pathInfo(Collections
                                    .singletonMap(outputDir.xml,"d:\\mp\\Ch05-Generator\\src\\main\\"))
                })

    }
}
