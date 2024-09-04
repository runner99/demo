package com.runner.testworks.controller.liaoling;

import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weizhenqiang
 * @date 2024/4/28 14:29
 */
@RequestMapping("/liaoning")
@RestController
public class LiaoNingController {

    @PostMapping("/test")
    public String test(){


        User user = new User();
        user.setId(12);
        user.setName("zzzhhh");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        JSONUtil.toJsonStr(user);

        return JSONUtil.toJsonStr(user);

    }

}
