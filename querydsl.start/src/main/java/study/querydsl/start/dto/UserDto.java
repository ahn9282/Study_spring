package study.querydsl.start.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private int age;

    public UserDto() {
    }

    public UserDto(String username, int age) {
        this.name = username;
        this.age = age;
    }
}
