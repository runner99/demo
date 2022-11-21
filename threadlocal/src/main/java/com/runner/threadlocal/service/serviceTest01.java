package com.runner.threadlocal.service;


import com.runner.threadlocal.common.UserEntity;
import com.runner.threadlocal.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

@Service
public class serviceTest01 {

    public void test01(){
        UserEntity userEntity = ThreadLocalUtil.get();

    }
}
