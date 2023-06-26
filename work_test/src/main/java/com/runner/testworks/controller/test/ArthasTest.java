package com.runner.testworks.controller.test;

import com.runner.testworks.config.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weizhenqiang
 * @date 2023/6/19 11:43
 */
@RestController
@RequestMapping("/arthas")
public class ArthasTest {



    @RequestMapping("/")
    public Result test01(){

        return Result.success(null);
    }




}
