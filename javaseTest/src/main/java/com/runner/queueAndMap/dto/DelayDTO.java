package com.runner.queueAndMap.dto;



import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author weizhenqiang
 * @date 2023/12/29 15:24
 */

public class DelayDTO implements Delayed {

    private Long expireTime;

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public DelayDTO() {
    }

    public DelayDTO(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}

