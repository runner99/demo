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
    create_time       datetime default (now()) not null,
    update_time       datetime default (now()) not null,
    out_template_id  bigint                      null,
    cron             varchar(255)             null,
    data_range_start int                      null,
    data_range_end   int                      null
    );