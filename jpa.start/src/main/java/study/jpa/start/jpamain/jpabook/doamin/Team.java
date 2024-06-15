package study.jpa.start.jpamain.jpabook.doamin;


import jakarta.persistence.*;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    //일대다               주인이 아닌 쪽은 읽기만 가능 mappedBy는 주인이 가지고 있지 않는다. 주로 외래키가 있는 곳을 주인으로 정한다.
    @OneToMany(mappedBy = "team")//User Entity 관계 변수 지정 해당 User 의 team이 연관관계 주인으로 지정
    private List<User> members = new ArrayList<>();// 초기화로 null 방지


    public void addMember(User member) {
        member.setTeam(this);
        members.add(member);
    }
}


