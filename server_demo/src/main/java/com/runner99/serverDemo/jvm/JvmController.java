package com.runner99.serverDemo.jvm;

import com.runner99.serverDemo.common.Result;
import com.runner99.serverDemo.jvm.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author weizhenqiang
 * @date 2023/10/10 14:04
 */
@Slf4j
@RestController
@RequestMapping("/jvm")
public class JvmController {

    private static boolean flag=true;

    private static CopyOnWriteArrayList<User> list=new CopyOnWriteArrayList<User>();

    @GetMapping("/oom")
    public void oom(){
        log.info("开始oom");
        flag=true;
        while (flag){
            list.add(new User(System.nanoTime(),"渣渣辉"+System.nanoTime()));
        }
    }

    @GetMapping("/oomStop")
    public void oomStop(){
        flag=false;
        log.info("oom结束,list集合中共产生{}个user对象",list.size());
        list.clear();

    }

    public static void main(String[] args) {
        ArrayList<User> list = new ArrayList<>();
        User user = new User(System.nanoTime(), "jkl");
        log.info(user.toString());
    }


}
