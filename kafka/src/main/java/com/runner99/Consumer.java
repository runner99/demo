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
        props.put("bootstrap.servers", "192.168.152.145:9092");
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
            }
        }


    }
}
