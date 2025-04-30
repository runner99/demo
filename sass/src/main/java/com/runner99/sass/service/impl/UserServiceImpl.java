package com.runner99.sass.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.runner99.sass.domain.User;
import com.runner99.sass.service.UserService;
import com.runner99.sass.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 28252
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-04-14 16:44:02
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




