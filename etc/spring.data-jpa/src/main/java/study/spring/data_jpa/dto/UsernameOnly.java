package study.spring.data_jpa.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
