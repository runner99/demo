package com.runner99.optional;

import com.runner99.pojo.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * @author weizhenqiang
 * @date 2023/4/19 15:31
 */
public class OptionalTest {
    @Test
    public void test01() {
        User user = null;
        Optional.ofNullable(user).ifPresent(obj->{
            System.out.println(obj.getName());
        });

    }

    @Test
    public void test02() {
        ArrayList<User> list = new ArrayList<>();
//        list.add(new User(1,"h"));
//        list.add(new User(2,"h"));
        list.add(null);
        Optional.ofNullable(list).orElse(new ArrayList<User>()).forEach(obj -> {
            System.out.println(Optional.ofNullable(obj).orElse(new User()));
        });
        list.stream().forEach(obj -> {
            System.out.println(obj.getName());
        });

    }

    @Test
    public void test03() {
        String str = null;
        int length = Optional.ofNullable(str).orElse("").length();
        System.out.println(length);


    }
}
