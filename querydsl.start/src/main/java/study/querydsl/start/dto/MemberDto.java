package study.querydsl.start.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {
    private String username;
    private int age;

    public MemberDto() {
    }

    //해당 어노테이션이 있으면 Dto도 QDto로 쿼리 dsl에 등록한다.
    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
