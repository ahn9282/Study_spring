package study.REST_API.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.REST_API.domain.User;

import java.util.List;

@Data
@NoArgsConstructor
public class AllUserResearchDto {
    private List<User> users;
    private Integer total;
}
