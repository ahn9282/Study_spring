package study.querydsl.start.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (this.team != null) {
            changeTeam(team);
        }else{
            this.team = team;
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);

    }
}
