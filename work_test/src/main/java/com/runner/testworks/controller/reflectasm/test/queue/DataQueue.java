package com.runner.testworks.controller.reflectasm.test.queue;

import com.runner.testworks.controller.reflectasm.pojo.ReflectasmEntity;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author weizhenqiang
 * @date 2023/7/5 10:26
 */
public class DataQueue {
    public static LinkedBlockingQueue<ReflectasmEntity> EVENT_QUEUE = new LinkedBlockingQueue<ReflectasmEntity>();
}
