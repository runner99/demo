package com.runner99.sql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.runner99.sql.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 28252
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-09-14 13:50:10
* @Entity generator.domain.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {
    int insertList(List<User> userList);

}




