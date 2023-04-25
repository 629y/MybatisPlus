# drop database if exists `mpdb`;
# create database `mpdb` default character set utf8;
# use `mpdb`;
#
# # SET NAMES utf8mb4;
# # SET FOREIGN_KEY_CHECKS = 0;
#
# DROP TABLE IF EXISTS `tag`;
# CREATE TABLE `tag`  (
#                         `id` int(11) NOT NULL AUTO_INCREMENT,
#                         `name` varchar(50) COMMENT '标签名字',
#                         `type` int(11) NULL DEFAULT NULL COMMENT '所属类别：0文章，1类别',
#                         PRIMARY KEY (`id`) USING BTREE
# ) ENGINE = InnoDB COMMENT = '标签';
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