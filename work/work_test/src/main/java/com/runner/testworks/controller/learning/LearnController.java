package com.runner.testworks.controller.learning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author weizhenqiang
 * @date 2023/11/20 10:25
 */
@RestController
@Slf4j
@RequestMapping("/learning")
public class LearnController {


    /**
     * 注意：必须在上一个stopWatch执行stop后才能执行下一个start
     * <p>
     * 2023-11-20 10:40:26.599  INFO 22440 --- [nio-8088-exec-1] c.r.t.c.learning.LearnController         : StopWatch 'test stopwatch': running time (millis) = 2004
     * -----------------------------------------
     * ms     %     Task name
     * -----------------------------------------
     * 01001  050%  test01
     * 01003  050%  test02
     */
    @GetMapping("/stopwatch")
    public void stopwatch() {
        StopWatch stopWatch = new StopWatch("test stopwatch");
        stopWatch.start("test01");
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            log.info("" + e);
        }
        stopWatch.stop();


        stopWatch.start("test02");
        try {
            Thread.sleep(1000L);
        } catch (Exception e) {
            log.info("" + e);
        }
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }



    @GetMapping("/login")
    public void login(@RequestParam(value = "url")String url, HttpServletResponse response){
        log.info(url);
//        https://192.168.52.204/tenant/initten/soc/dict
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/loginForwad")
    public void loginForwad(HttpServletRequest request, HttpServletResponse response){

        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forward?method=forward");
            requestDispatcher.forward(request,response);

        }catch (Exception e){

        }
    }
    public static void main(String[] args) {

//        多线程测试

        int core = 5;
        ExecutorService service = Executors.newFixedThreadPool(core);
        for (int i = 0; i < core; i++) {
            service.execute(() -> {
                log.info(Thread.currentThread().getName());
                StopWatch stopWatch = new StopWatch("test stopwatch");

                stopWatch.start(Thread.currentThread().getName());
                try {
                    Thread.sleep(Long.parseLong(Thread.currentThread().getName().substring(Thread.currentThread().getName().length() - 1))*1000);

                } catch (Exception e) {
                    log.info("" + e);
                }
                stopWatch.stop();
                log.info(stopWatch.prettyPrint());
                log.info(Thread.currentThread().getName());

            });
        }


        log.info("ggggggggggggggggggg");


    }
}
