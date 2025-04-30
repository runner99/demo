package com.runner99.kafka.protobufClient;

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
public class ConsumerBuf {



    public static void main(String[] args) {
        Properties props = new Properties();



        props.put("bootstrap.servers", ProductorBuf.KAFKA_IP);
        props.put("group.id", "id01");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("max.poll.records", 1000);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", ByteArrayDeserializer.class.getName());


        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<String, byte[]>(props);


        consumer.subscribe(Arrays.asList(ProductorBuf.TOPIC));

        while (true){
            ConsumerRecords<String, byte[]> records=consumer.poll(1000);
            for (ConsumerRecord<String, byte[]> record : records){
                System.out.println("接收key:"+record.key());
                String data = new String(record.value());
                System.out.println("接收value:"+data);
                System.out.println("接收value字节长度："+data.getBytes().length);

            }
//            try {
//                Thread.sleep(1000L);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }


    }
}
