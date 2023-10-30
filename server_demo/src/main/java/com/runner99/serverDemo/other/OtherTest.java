package com.runner99.serverDemo.other;

import com.runner99.serverDemo.jvm.pojo.User;

import java.util.ArrayList;

/**
 * @author weizhenqiang
 * @date 2023/10/25 9:27
 */
public class OtherTest {
    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add(new User((long) (i+1),"name"+i+1));
        }


    }
}
