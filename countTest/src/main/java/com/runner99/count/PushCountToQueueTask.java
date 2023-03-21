package com.runner99.count;

/**
 * @author weizhenqiang
 * @date 2023/3/20 16:35
 */
public class PushCountToQueueTask implements Runnable{
    private final CountService countService;

    public PushCountToQueueTask(CountService countService) {
        this.countService = countService;
    }


    @Override
    public void run() {
        countService.push();
    }
}
