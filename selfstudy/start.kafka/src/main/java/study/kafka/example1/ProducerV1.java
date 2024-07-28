package study.kafka.example1;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerV1 {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void create(String targetTopic, String messageContent){
        kafkaTemplate.send(targetTopic, messageContent);
    }
}
