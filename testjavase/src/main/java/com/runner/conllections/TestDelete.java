package com.runner.conllections;

import com.runner.pojo.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class TestDelete {

    //测试foreach与iterator删除元素
    @Test
    public void test01(){
        ArrayList<User> list = new ArrayList<User>();
        for (int i=0;i<10;i++){
            User user = new User(i, "username" + i);
            list.add(user);
        }
        System.out.println(list.toString());

        //不可在foreach中删除，添加元素,会报错
        for (User user:list){
            if (user.getId()==3){
//                list.remove(user);
            }
        }

        Iterator<User> it = list.iterator();
        while (it.hasNext()){
            User user = it.next();

//            if (user.getId()==3){
                it.remove();

//            }
        }
        System.out.println(list.toString());
    }

    @Test
    public void demo02(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        for (Integer integer : list) {
            System.out.println(integer);
            if(integer.equals(0)){
                list.remove(integer);
            }
        }
        System.out.println(list);

//        ArrayList<Integer> list = new ArrayList<Integer>();
//        for (int i = 0; i < 5; i++) {
//            list.add(i);
//        }
//
//        for (Integer integer : list) {
//            System.out.println(integer);
//            if(integer.equals(3)){
//                list.remove(integer);
//            }
//        }
//        System.out.println(list);
    }
}
