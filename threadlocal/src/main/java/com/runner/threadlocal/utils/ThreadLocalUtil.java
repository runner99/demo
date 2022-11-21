package com.runner.threadlocal.utils;


import com.runner.threadlocal.common.UserEntity;

public class ThreadLocalUtil {
    public static ThreadLocal<UserEntity> threadLocal = new ThreadLocal<> ();

    public static void setUserEntity(UserEntity entity){
        threadLocal.set (entity);
    }

    public static void clear(){
        threadLocal.remove ();
    }

    public static UserEntity get(){
        return threadLocal.get();
    }
}
