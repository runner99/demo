package com.runner.testworks.controller.chunan;

import com.runner.testworks.config.Result;
import com.runner.testworks.controller.chunan.vo.OutSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author weizhenqiang
 * @date 2023/7/27 10:11
 */
@RestController
@RequestMapping("/chunan")
@Slf4j
public class ChunAnController {

    @GetMapping("/test")
    public Result test(){
        log.info("test haha");
        return Result.success("test");
    }
    @PostMapping("/receiveMsg")
    public String receiveMsg(@RequestBody OutSend outSend){
        int i=0;
//        int i1 = 12/i;
        System.out.println(outSend.toString());
        return "testssss";
    }

    public static void main(String[] args) {
        Random random = new Random(1);

    }
}
