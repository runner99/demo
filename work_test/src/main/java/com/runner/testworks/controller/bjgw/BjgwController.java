package com.runner.testworks.controller.bjgw;

import com.runner.testworks.controller.bjgw.vo.Result;
import com.runner.testworks.pojo.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author weizhenqiang
 * @date 2023/7/31 19:39
 */
@RestController
@RequestMapping("/bjgw")
public class BjgwController {

    public static boolean isGlobal;


    @PostMapping("/test")
    public Result test(@RequestBody Map map){
        String s = (String) map.get("sql");


        return Result.ofSuccess(s);
    }

    public static void main(String[] args) {


    }
}
