package com.runner99.sql.controller;


import com.runner99.sql.common.Result;
import com.runner99.sql.mapper.RiskOutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weizhenqiang
 * @date 2024/2/18 11:17
 */
@RestController("/sql")
public class SqlController {


    @Autowired
    private RiskOutMapper riskOutMapper;
    @GetMapping(value = "/getById")
    public Result getById(@RequestParam Long id){
        return Result.success(riskOutMapper.selectByPrimaryKey(id));
    }

    @GetMapping(value = "/getByName")
    public Result getByName(@RequestParam String name){
        return Result.success(riskOutMapper.selectByName(name));
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.timezone"));
    }

}
