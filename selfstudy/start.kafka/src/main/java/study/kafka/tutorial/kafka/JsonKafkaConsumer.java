package study.kafka.tutorial.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import study.kafka.tutorial.payload.User;

@Slf4j
@Service
public class JsonKafkaConsumer {

    @KafkaListener(topics="test-json", groupId = "my-group")
    public void consume(User user){

        log.info("received User's Info : {}", user);
    }
}
