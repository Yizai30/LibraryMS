-- mysql database
create table user_info (
                           id           bigint auto_increment comment 'id'
                               primary key,
                           userAccount  varchar(256)                       null comment '账号',
                           userPassword varchar(512)                       not null comment '密码',
                           userRole     int      default 0                 not null comment '用户角色：0-普通用户，1-管理员',
                           createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
                           updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
                           isDelete     tinyint  default 0                 not null comment '是否删除',
)
    comment '用户';

-- h2 database
create table user_info (
                           id           bigint auto_increment comment 'id'
                               primary key,
                           userAccount  varchar(256)                       not null comment '账号',
                           userPassword varchar(512)                       not null comment '密码',
                           userRole     int      default 0                 not null comment '用户角色：0-普通用户，1-管理员',
                           createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
                           updateTime   datetime not null AS CURRENT_TIMESTAMP comment '更新时间',
                           isDelete     tinyint  default 0                 not null comment '是否删除：0-未删除，1-删除'
)
    comment '用户';

create table book_info (
                           id           bigint auto_increment comment 'id'
                               primary key,
                           bookName  varchar(256)                          not null comment '书籍名称',
                           bookDescription varchar(512)                    not null comment '书籍描述',
                           createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
                           updateTime   datetime not null AS CURRENT_TIMESTAMP comment '更新时间',
                           isDelete     tinyint  default 0                 not null comment '是否删除：0-未删除，1-删除'
)
    comment '书籍';