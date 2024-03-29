package com.runner99;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.*;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author weizhenqiang
 * @date 2023/4/9 23:09
 */
public class Consumer {
    public static void main(String[] args) {
        Properties props = new Properties();


//      kafka认证配置
        String username="alice";
        String passwd="hzmc456";
        props.setProperty("security.protocol", "SASL_PLAINTEXT");
        props.setProperty("sasl.mechanism", "PLAIN");
        props.put("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + username
                        + "\" password=\"" + passwd + "\";");

//        props.put("enable.auto.commit","true");
//        props.put("auto.commit.interval.ms","1000");
//        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");


        props.put("bootstrap.servers", "192.168.52.201:9092");
        props.put("group.id", "id01");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", ByteArrayDeserializer.class.getName());


        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);


        consumer.subscribe(Arrays.asList("test_topic"));

        while (true){
            ConsumerRecords<String, byte[]> records=consumer.poll(500);
            for (ConsumerRecord<String, byte[]> record : records){
                System.out.println("接收key:"+record.key());
                System.out.println("接收value:"+new String(record.value()));
                String s = new String(record.value());
                System.out.println(s);
            }
        }


    }
}
