前提：master和slave已经部署到服务器并启动，并且保证server-id生效

# 1.主机配置：

## 1.1.创建同步数据的用户slave1

```sql
CREATE USER 'slave1'@'%' IDENTIFIED BY '123456';
GRANT REPLICATION SLAVE ON *.* TO 'slave1'@'%';
#此语句必须执行。否则见下面。
ALTER USER 'slave1'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
flush privileges;
```

## 1.2.查看master状态，记录下同步的binlog信息

```mysql
show master status;
```

> 记录下File和Position字段的值
>



# 2.从机配置

## 2.1.sql

```sql
CHANGE MASTER TO
 MASTER_HOST='192.168.52.193',MASTER_USER='slave1',MASTER_PASSWORD='123456',MASTER_LOG_FILE='binlog.000011',MASTER_LOG_POS=1196;
```

## 2.3.sql字段详解

MASTER_HOST:主机master的ip

MASTER_USER：主机创建的用户

MASTER_PASSWORD：用户的密码

MASTER_LOG_FILE：同步的binlog日志，File字段

MASTER_LOG_POS：Position字段



## 2.2.启动同步

```sql
start slave ;
```



2.3.查看同步状态

```sql
SHOW SLAVE STATUS;
```

当Slave_IO_Runing和Slave_SQL_Running都是 **Yes**的时候说明同步成功

test