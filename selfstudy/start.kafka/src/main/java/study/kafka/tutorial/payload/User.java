package study.kafka.tutorial.payload;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String firstName;
    private String lastName;

}
