package study.kafka.basic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.retrytopic.DestinationTopic;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class ProductDemo {

    public static void main(String[] args) {

        log.info("Hello World!!");

        //create Producer Properties
        Properties properties = new Properties();

        //set producer properties
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        //create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //create a Producer Record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test-topic", "hello world");

        //send data
        producer.send(producerRecord, (metadata, exception) -> {
            if (exception != null) {
                log.error("Faile", exception);
            } else {
                log.info("Message sent to topic {} with offset {}", metadata.topic(), metadata.offset());
            }
        });

        // tell the producer to send all data and block untill done -- synchronous
        producer.flush();

        //flush and close the producer
        producer.close();

        log.info(" ================================ Response ==========================");

        // Consumer 설정
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Consumer 생성
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties);

        // 토픽 구독
        consumer.subscribe(Collections.singletonList("test-topic"));

        // 메시지 폴링 및 처리
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    log.info("Consumed message: key = {}, value = {}, topic = {}, partition = {}, offset = {}",
                            record.key(), record.value(), record.topic(), record.partition(), record.offset());
                }
            }
        } catch (WakeupException e) {
            log.info("Shutdown signal received");
        } finally {
            consumer.close();
        }
    }
}
