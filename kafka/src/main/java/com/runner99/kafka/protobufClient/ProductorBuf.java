package com.runner99.kafka.protobufClient;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.alibaba.fastjson2.JSONObject;

import java.util.Properties;

/**
 * @author weizhenqiang
 * @date 2023/10/9 18:14
 */
@Slf4j
public class ProductorBuf {

    public static final String KAFKA_IP="192.168.52.123:9092";


    public static final String TOPIC ="bufTest";


    public static void main(String[] args) {


        Properties props = new Properties();
        props.put("bootstrap.servers", KAFKA_IP);
        props.put("acks", "1");
        props.put("linger.ms", 0);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        props.put("value.serializer",
                "org.apache.kafka.common.serialization.ByteArraySerializer");


        KafkaProducer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);





        while (true){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name","xiaozhang"+System.currentTimeMillis());
            jsonObject.put("time",System.currentTimeMillis());

            try {
                producer.send(new ProducerRecord<String, byte[]>(TOPIC, jsonObject.toJSONString().getBytes()));
                System.out.println("发送消息:"+jsonObject.toJSONString());
//                Thread.sleep(1000);
            } catch (Throwable e) {
                log.error("消息发送异常"+e.getMessage());
            }

        }


    }
}
