package com.runner99.sql.controller;

import com.runner99.sql.domain.User;
import com.runner99.sql.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author weizhenqiang
 * @date 2024/2/18 11:17
 */
@RestController("/sql")
@Slf4j
public class SqlController {

    @Resource
    private UserMapper userMapper;

    private AtomicBoolean insert = new AtomicBoolean(Boolean.TRUE);

    @GetMapping("/insert")
    public void insert() {

        insert = new AtomicBoolean(Boolean.TRUE);
        LinkedBlockingQueue<User> queus = new LinkedBlockingQueue<>(20000);

        Integer produceCore=5;
        ExecutorService executorService1 = Executors.newFixedThreadPool(produceCore);
        for (int i=0;i<produceCore;i++){
            executorService1.execute(()->{
                while (Boolean.TRUE){
                    try {
                        queus.put(new User("name"+System.nanoTime()));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        Integer core = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(core);
        for (int i = 0; i < core; i++) {
            executorService.execute(() -> {
                while (insert.get()) {
                    ArrayList<User> users = new ArrayList<>();
                    queus.drainTo(users, 10000);

                    if (!CollectionUtils.isEmpty(users)){
                        userMapper.insertList(users);

                    }
                }
            });
        }
    }

    @GetMapping("/insertStop")
    public void insertStop() {
        insert = new AtomicBoolean(Boolean.FALSE);
    }

}
