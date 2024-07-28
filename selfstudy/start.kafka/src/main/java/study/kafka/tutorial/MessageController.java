package study.kafka.tutorial;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.kafka.tutorial.kafka.JsonKafkaProducer;
import study.kafka.tutorial.kafka.KafkaProducer;
import study.kafka.tutorial.payload.User;

@Slf4j
@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {

    private KafkaProducer kafkaProducer;
    private JsonKafkaProducer jsonKafkaProducer;

    public MessageController(JsonKafkaProducer jsonKafkaProducer, KafkaProducer kafkaProducer) {
        this.jsonKafkaProducer = jsonKafkaProducer;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body("메시지 전송 : " + message);
    }

    @GetMapping("/json")
    public ResponseEntity<String> publishJson( User data){
        log.info("data : {}", data);
        jsonKafkaProducer.sendMessage(data);

        return ResponseEntity.status(HttpStatus.OK).body("메시지 전송 : " + data.toString());
    }

}
