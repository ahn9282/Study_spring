package study.spring.data_jpa.dto;

public class UsernameOnlyDto {
    private final String username;
    public UsernameOnlyDto(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}