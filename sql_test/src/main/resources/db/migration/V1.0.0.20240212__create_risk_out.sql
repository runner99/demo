CREATE TABLE if NOT EXISTS risk_out
(
    id               bigint auto_increment
    primary key,
    name             varchar(255)             null,
    out_type         varchar(255)             null,
    out_ip           varchar(255)             null,
    out_port         varchar(255)             null,
    out_protocol     varchar(255)             null,
    enable           tinyint(1)               null,
    createTime       datetime default (now()) not null,
    updateTime       datetime default (now()) not null,
    out_template_id  int                      null,
    cron             varchar(255)             null,
    data_range_start int                      null,
    data_range_end   int                      null
    );