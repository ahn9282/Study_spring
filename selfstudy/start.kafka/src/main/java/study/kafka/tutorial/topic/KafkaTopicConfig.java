package study.kafka.tutorial.topic;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic javaGuildTopic() {
        return TopicBuilder.name("test-study")
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic jsonTopic() {
        return TopicBuilder.name("test-json")
                .partitions(5)
                .build();
    }
}
