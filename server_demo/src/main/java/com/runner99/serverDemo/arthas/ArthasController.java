package com.runner99.serverDemo.arthas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

/**
 * @author weizhenqiang
 * @date 2023/10/20 11:28
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class ArthasController {

    public static boolean flag=true;
    @GetMapping("/test01")
    public void test01(){
        flag=true;
        while (flag){
            System.out.println(LocalTime.now().toString());
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("/test02")
    public void test02(){
        flag=false;
    }

    public static void main(String[] args) {
        System.out.println(LocalTime.now().toString());
    }

}
