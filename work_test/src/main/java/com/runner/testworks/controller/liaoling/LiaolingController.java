package com.runner.testworks.controller.liaoling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weizhenqiang
 * @date 2024/1/10 19:39
 */
@RestController
@RequestMapping("liaoling")
@Slf4j
public class LiaolingController {


    @PostMapping("testUrl")
    public String testUrl(@RequestParam("token") String token){
        log.info(token);
        return "hhhhhhh";
    }
}
