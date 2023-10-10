package com.runner99;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.runner99.pojo.Home;
import com.runner99.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author weizhenqiang
 * @date 2023/4/9 23:09
 */
@Slf4j
public class Productor {
    public static void main(String[] args) {


        String username="alice";
        String passwd="hzmc456";


        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.52.201:9092");
        props.put("acks", "1");
        props.put("linger.ms", 0);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.ByteArraySerializer");


        //        认证配置
        props.setProperty("security.protocol", "SASL_PLAINTEXT");
        props.setProperty("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username
                        + "\" password=\"" + passwd + "\";");


        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);





        for (int i = 0; i < 1000; i++) {

            User user = new User();
            user.setId(""+i);
            user.setName("name"+i);


            ArrayList<Home> list = new ArrayList<>();


            Home home1 = new Home();
            home1.setName("home1"+i);

            Home home2 = new Home();
            home2.setName("home2"+i+"\r\n");

            list.add(home1);
            list.add(home2);
            user.setHome(list);


//            String send = JSON.toJSONString(list);
            String send = "test\r\n"+System.currentTimeMillis();


            try {
                producer.send(new ProducerRecord<String, byte[]>("test_topic", send.getBytes()));
                System.out.println("发送消息:"+send);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                log.error(e.getMessage());
            }
        }

        producer.close();
    }
}
