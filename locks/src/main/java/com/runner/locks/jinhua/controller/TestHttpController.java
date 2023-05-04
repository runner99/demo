package com.runner.locks.jinhua.controller;

import com.runner.locks.config.Result;
import com.runner.locks.jinhua.pojo.Person;
import com.runner.locks.jinhua.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @author weizhenqiang
 * @date 2023/4/24 17:16
 */
@RestController
public class TestHttpController {

    @GetMapping("test01")
    public Result test01(HttpServletRequest request){
        String test01 = request.getParameter("test01");
        String test02 = request.getParameter("test02");
        User user = new User();
        user.setId(Integer.parseInt(test01));
        user.setName(test02);
        return Result.success(user);
    }


    @GetMapping("test02")
    public Result test02(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");

        Person person = new Person();
        person.setId(Integer.parseInt(id));
        person.setName(name);
        person.setGender(gender);
        return Result.success(person);
    }

    @GetMapping("test03")
    public Result test03(HttpServletRequest request){
        ArrayList<User> list = new ArrayList<>();
        User user01 = new User(1, "haah");
        User user02 = new User(2, "gg");

        list.add(user01);
        list.add(user02);
        return Result.success(user01);
    }

    @GetMapping("test04")
    public Result test04(){
        ArrayList<User> list = new ArrayList<>();
        for (int i = 0;i<3;i++){
            User user = new User();
            user.setId(i);
            user.setName("name"+i);
            list.add(user);

        }
        return Result.success(list);
    }


}
