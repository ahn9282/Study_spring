package study.spring.data_jpa.dto;

import lombok.Data;
import study.spring.data_jpa.entity.Member;
import study.spring.data_jpa.entity.Team;

@Data
public class MemberDto {

    private Long id;
    private String username;
    private String teamName;

    public MemberDto() {
    }

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDto(Member member) {
        username = member.getUsername();
        this.id = member.getId();
        this.teamName = member.getTeam().getName();
    }

}
