package study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import study.repository.WikimediaDataRepository;

@Service
@Slf4j
public class KafkaDatabaseConsumer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final WikimediaDataRepository wikimediaDataRepository;

    public KafkaDatabaseConsumer(KafkaTemplate<String, String> kafkaTemplate, WikimediaDataRepository wikimediaDataRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.wikimediaDataRepository = wikimediaDataRepository;
    }

    @KafkaListener(topics="wikimedia_recentChange", groupId = "my-group")
    public void consume(String eventMessage){

        log.info("Event Message!! -> {}", eventMessage);
        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage.substring(0, 100));

        wikimediaDataRepository.save(wikimediaData);
    }
}
