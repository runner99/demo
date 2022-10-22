package com.runner.locks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class Testservices01 {

    @Autowired
    private RedisTemplate redisTemplate;



}
