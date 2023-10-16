package com.runner.reflect;

import com.runner.pojo.User;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author weizhenqiang
 * @date 2023/3/27 19:56
 */
public class Reflect {
    @Test
    public void test01() throws Exception {
        User user = new User(1, "haha");
        Field[] id = user.getClass().getDeclaredFields();
        Arrays.stream(id).forEach(field->{
            field.setAccessible(true);
            System.out.println(field);
            Object o = null;
            try {
                o = field.get(user);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            System.out.println(o);
        });
//        id.setAccessible(true);
//        Object o = id.get(user);
//        System.out.println(o);
//        System.out.println(id);

    }
}
