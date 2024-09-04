package com.runner.testworks.controller.liaoling;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author weizhenqiang
 * @date 2023/10/9 18:14
 */
@Slf4j
public class Consumer {


    private static final String TOPIC="external_operation_log";
    private static final String KAFKA_IP="192.168.152.201:9092";

    public static void main(String[] args) {
        Properties props = new Properties();


        props.put("bootstrap.servers", KAFKA_IP);
        props.put("group.id", "id01");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", ByteArrayDeserializer.class.getName());


        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);


        consumer.subscribe(Arrays.asList(TOPIC));

        while (true){
            ConsumerRecords<String, byte[]> records=consumer.poll(500);
            for (ConsumerRecord<String, byte[]> record : records){
//                System.out.println("接收key:"+record.key());
                System.out.println("接收value:"+new String(record.value()));
                log.info(new String(record.value()));
//                String s = new String(record.value());
//                System.out.println(s);
            }
        }


    }
}
