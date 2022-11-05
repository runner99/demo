package com.runner99.java8;

import com.runner99.java8.pojo.User;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * stream流学习
 */
public class TestStream {


    //集合中长度以及筛选遍历
    @Test
    public void test01() {
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("小昭");
        list.add("殷离");
        list.add("张三");
        list.add("张三丰");
        //返回的是long，为集合的长度
        System.out.println(list.stream().count());
        list.stream()
                .filter(name -> name.startsWith("张"))
                //name或者max不影响结果
                .filter(max -> max.length() == 3)
                .forEach(name -> System.out.println(name));
    }

    //顺序流与并行流的区别
    @Test
    public void test02(){
        List<String> list = new ArrayList<>();
        list.add("张无忌");
        list.add("周芷若");
        list.add("赵敏");
        list.add("小昭");
        list.add("殷离");
        list.add("张三");
        list.add("张三丰");

        //stream为顺序流，输出与输入顺序一致
        list.stream().forEach(name-> System.out.println(name));
        System.out.println("-----------------");
        System.out.println("-----------------");
        //stream为并行流，输出与输入顺序不一致
        list.parallelStream().forEach(name -> System.out.println(name));
    }

    //结合实体类进行筛选遍历
    @Test
    public void test03(){
        List<User> list = new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add(new User(i,"渣渣辉"+i+"号"));
        }
        ArrayList<User> users = new ArrayList<>();
        list.stream()
//                .filter(user -> user.getName().equals("渣渣辉8号")&&user.getId()==8)
                .filter(user -> user.getName().startsWith("渣渣"))
                .forEach(user->users.add(user));
        //并行流输出
//        list.parallelStream().forEach(System.out::println);
        System.out.println(users);
    }

}
