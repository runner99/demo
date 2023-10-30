package com.runner99.serverDemo.arthas;

import com.runner99.serverDemo.arthas.testutils.UtilsTest;
import com.runner99.serverDemo.common.Result;
import com.runner99.serverDemo.jvm.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

/**
 * @author weizhenqiang
 * @date 2023/10/20 11:28
 */
@Slf4j
@RestController
@RequestMapping("/arthas")
public class ArthasController {

    @GetMapping("/test01")
    public Result<String> test01() {
        log.info("test compile");
        return Result.ofSuccess(UtilsTest.testString());

    }

    @GetMapping("/test02")
    public Result<String> test02(@RequestParam(value = "name", required = false) String name) {
        return Result.ofSuccess(name);
    }

    @PostMapping("/test03")
    public Result<User> test03(@RequestBody User user) {
        return Result.ofSuccess(user);
    }

    @GetMapping("/test04")
    public Result<String> test04() {
        int i = 0;
        int i1 = 12 / i;
        return Result.ofFailed("gggg");
    }

    @GetMapping("/test05")
    public Result<String> test05() {
        try {
            int i = 0;
            int i1 = 12 / i;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }

        return Result.ofFailed("gggg");
    }

    @GetMapping("/test06")
    public Result<String> test06() {
        if (log.isDebugEnabled()){
            System.out.println("debug");
        }
        return Result.ofFailed("gggg");
    }

    public static void main(String[] args) {
        System.out.println(LocalTime.now().toString());
    }

}
