package com.runner99.sql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.runner99.sql.domain.User;
import com.runner99.sql.mapper.UserMapper;
import com.runner99.sql.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author 28252
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-09-14 13:50:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




