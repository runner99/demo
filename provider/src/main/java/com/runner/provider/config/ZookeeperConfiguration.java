package com.runner.provider.config;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfiguration {

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private  int timeout;

    private boolean SERVER_OFFLINE = false;

    @Bean(name = "zkClient")
    public ZkClient zkClient(){
        ZkClient zkClient= null;

        try {
            zkClient = new ZkClient (connectString, 30000, 30000, new SerializableSerializer ());
            zkClient.subscribeStateChanges(new Zklistener());
        }catch (Exception e){
            // 本地连接器
            System.out.println("检测到离线状态");
            zkClient = new ZkClient ("127.0.0.1:2181", 3000, 3000, new SerializableSerializer ());
            SERVER_OFFLINE = true;
        }
        return zkClient;
    }

}