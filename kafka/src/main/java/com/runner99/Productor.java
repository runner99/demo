package com.runner99;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.runner99.pojo.Home;
import com.runner99.pojo.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author weizhenqiang
 * @date 2023/4/9 23:09
 */
public class Productor {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.152.145:9092");
        props.put("acks", "all");
        props.put("retries", 0);
//        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", ByteArraySerializer.class.getName());
        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);

        for (int i = 0; i < 1000; i++) {

            User user = new User();
            user.setId(""+i);
            user.setName("name"+i);


            ArrayList<Home> list = new ArrayList<>();


            Home home1 = new Home();
            home1.setName("home1"+i);

            Home home2 = new Home();
            home2.setName("home2"+i);

            list.add(home1);
            list.add(home2);
            user.setHome(list);


            String send = JSON.toJSONString(list);

            producer.send(new ProducerRecord<String, byte[]>("test_topic", send.getBytes()));
            System.out.println("发送消息:"+send);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        producer.close();
    }
}
