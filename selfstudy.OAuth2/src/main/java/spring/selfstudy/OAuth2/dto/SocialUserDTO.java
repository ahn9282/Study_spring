package spring.selfstudy.OAuth2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserDTO {

    private Long id;

    private String username;

    private  String email;

    private String role;

}
