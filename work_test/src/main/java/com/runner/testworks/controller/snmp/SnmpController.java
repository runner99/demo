package com.runner.testworks.controller.snmp;

import com.runner.testworks.config.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weizhenqiang
 * @date 2023/11/13 15:09
 */

@RestController
@RequestMapping("/snmp")
public class SnmpController {


    @GetMapping("/getInfo")
    public Result getInfo(){

        return Result.success(null);
    }


}
