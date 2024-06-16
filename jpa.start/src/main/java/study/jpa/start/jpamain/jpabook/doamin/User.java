package study.jpa.start.jpamain.jpabook.doamin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    //값 타입 컬렉션
    //해당 값들을 다른 테이블로 하여 참조관계로 만든다.
    // 이렇게 추가된 테이블들은 해당 엔티티의 기본키를 참조하도록 한다.
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD",
            joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    //해당 값 타입 컬렉션의 경우 db 성능 상 문제가 생길 수 있어 일대다 연간 관계 엔티티로 해당 값 타입 컬렉션의 대안으로 사용하기도 한다.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "MEMBER_ID")
//    private List<AddressEntity> addressHistory = new ArrayList<>();
  /*  @ElementCollection
    @CollectionTable(name = "ADDRESS",
            joinColumns = @JoinColumn(name="MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();*/

    @Embedded
    private Period workPeriod;
    @Embedded//해당 객체의 변수들을 컬럼으로 담는다. 이에 경우 재사용이 가능하고 응집도 향상
   private Address homeAddress;
    @Embedded
    @AttributeOverrides({//어노테이션을 활용해 중복 방지 추가 재사용
            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE")),
            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREEET"))
    })
    private Address workAddress;
    

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
