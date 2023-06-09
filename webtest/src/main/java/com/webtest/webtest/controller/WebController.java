package com.webtest.webtest.controller;

import com.webtest.webtest.aspect.LogLoginType;
import com.webtest.webtest.aspect.LoginLog;
import com.webtest.webtest.config.RestTemplateConfig;
import com.webtest.webtest.config.Result;
import com.webtest.webtest.pojo.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author weizhenqiang
 * @date 2023/5/10 14:36
 */

@RestController
public class WebController {

    static RestTemplate httpsRestTemplate;

    static {
        try {
            httpsRestTemplate = new RestTemplate(RestTemplateConfig.generateHttpRequestFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/test01")
    public Result test01(){
        User user = new User();
        user.setId(1);
        user.setName("haha");
        return Result.success(user);
    }

    @PostMapping("/test02")
    public Result test02(@RequestBody User user){

        return Result.success(user);
    }

    @GetMapping("/test03/operation")
    public Result test03(User user){

        System.out.println(user);
        return Result.success(user);

    }

//    @GetMapping("/test04/{status}")
//    @LoginLog(operType = LogLoginType.LOGIN)
//    public Result test04(@PathVariable("status") Integer status){
//
//
//        if (status.equals(0)){
//            throw new RuntimeException("异常");
//        }
//
//
//        return Result.success("haha");
//
//    }

//    @GetMapping("/test05")
//    public Result test05(){
////        httpsRestTemplate.exchange()
//
//        return Result.success("haha");
//
//    }
//
//    @GetMapping("/test06")
//    public Result test06(@RequestParam(value = "ids[]") Integer[] ids){
////        httpsRestTemplate.exchange()
//        System.out.println(ids);
//
//        return Result.success("haha");
//
//    }
}
