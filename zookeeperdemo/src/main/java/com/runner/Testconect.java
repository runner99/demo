package com.runner;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class Testconect {
    public static void main(String[] args) {
        try {

            //计数器对象
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            /**
             * arg1:服务器的ip和端口
             * arg2：客户端与服务器之间的会话超时时间 以毫秒为单位
             * arg3：监视器对象
             */
            ZooKeeper zooKeeper = new ZooKeeper("192.168.191.128:2181", 5000, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
                        System.out.println("连接成功");
                        countDownLatch.countDown();
                    }
                }
            });
            //主线程阻塞等待连接对象的创建成功
            countDownLatch.await();
            //会话编号
            System.out.println(zooKeeper.getSessionId());
            zooKeeper.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
