package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MEMBERS")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    //fetch = FetchType.EAGER 에 경우 UserEntity loading 시 join 해서 즉시 같이 loading 시킨다.
    //왠만해서는 LAZY로 설정하고 필요한 부분에만 신중히 EAGER로 한다. @ManyToOne 경우 default 가 EAGER 이기에 굳이 해줘야한다.
    @ManyToOne(fetch = FetchType.LAZY) //User 입장에서는 many Team 입장에서는 One  일대다 관계
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "Locker_ID")
    private Locker locker;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<MemberProduct> memberProducts = new ArrayList<>();

    @Column(name = "username")
    private String name;




    @Override
    public String toString() { //JPA 에서 toString은 무한 루프를 발생시킬 수 있기에 최대한 자제하며 신중하게 써야한다.
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", team=" + (team != null ? team.getName() : "none") +
                '}';
    }

    //연간 관계 메서드로 메서드 자체에서 연간관계 적용을 묶는다.
//    public void setTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }

    /*@Column(name = "TEAM_ID")
    private Long teamId;*/
}
