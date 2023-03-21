package com.runner99.count;

/**
 * @author weizhenqiang
 * @date 2023/3/20 16:43
 */
public class CountTask implements Runnable {
    private final CountService countService;

    public CountTask(CountService countService) {
        this.countService = countService;
    }


    @Override
    public void run() {
        countService.count();
    }
}
