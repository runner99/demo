package com.runner99.kafka.client;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author weizhenqiang
 * @date 2024/2/27 11:44
 */
public class ClientTest {

    public static void main(String[] args) {


        String kafkaIp="192.168.152.202:9092";
        // 声明主题
        String topic = "device-alarm-test";
        // 创建消费者
        Properties consumerConfig = new Properties();
        consumerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaIp);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG,"boot-kafka");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfig);
        // 订阅主题并循环拉取消息
        kafkaConsumer.subscribe(Arrays.asList(topic));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10000));
                    for(ConsumerRecord<String, String> record:records){
                        System.out.println(record.value());
                    }
                }
            }
        }).start();
        // 创建生产者
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaIp);
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG,"boot-kafka-client");
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer<>(producerConfig);
        // 给主题发送消息
        producer.send(new ProducerRecord(topic, "hello，"+System.currentTimeMillis()));
    }

}
