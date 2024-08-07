package study.kafka.tutorial.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {


    @KafkaListener(topics="test-study", groupId = "my-group")
    public void consume(String message){

        log.info("Message received -> {}", message);
    }

}
