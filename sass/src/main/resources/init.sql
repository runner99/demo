create table user
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(255) null comment '名字',
    age         int          null comment '年龄',
    create_time bigint       null comment '创建时间'
);