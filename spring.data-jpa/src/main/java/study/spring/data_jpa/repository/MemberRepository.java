package study.spring.data_jpa.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import study.spring.data_jpa.dto.MemberDto;
import study.spring.data_jpa.dto.UsernameOnly;
import study.spring.data_jpa.entity.Member;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {


    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findByUsernameAndAgeGreaterThanAndAgeLessThan(String username, int age, int limit);

    //Top숫자의 경우 맨 앞 데이터 2개만 가져온다.
    List<Member> findTop3HelloBy();

    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.spring.data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    Slice<Member> findByAge(@Param("age") int age, Pageable pageable);

    // 해당 어노테이션은 executeUpdate() 메서드를 jpql 반호나 메서드로 설정한다.
    // 없으면 gerResult~ 가 적용됨 -> invalid ~ RuntimeException 뜬다.
    @Modifying(clearAutomatically = true)//해당 true 설정은 쿼리 실행 후 영속성 컨텍스트를 clear() 해준다
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m join fetch m.team t ")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
        //해당 설정을 하면 패치조인 jpql이 아니어도 연관관계인 엔티티를 지정해 자동 패치조인시킬 수 있다.
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
// 이것도 결과가 위와 같다.
    List<Member> findMemberEntityGraph();

    //@EntityGraph(attributePaths = {"team"})//이것도 지연로딩이 아닌 즉시로 패치 조인처럼 가져오도록 할 수 있다.
    @EntityGraph("Member.all")
//@NamedEntityGraph 사용
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    //이 경우 변경감지를 무시한다. -> @Transactional(readOnly = true) 되도록 한다 봐도 무방
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

    //제네릭을 활용하여 프로젝션을 자유롬게 사용할 수 있다.
    <T> List<T> findProjectionByUsername(@Param("username") String username, Class<T> type);

    @Query(value = "select * from member where username = ?", nativeQuery = true)
    Member findByNativeQuery(String username);

    @Query(value = "select m.member_id as id , m.username, t.name as teamName " +
            " from member m left join team t on m.team_id = t.team_id ",
            countQuery = "select count(*) from member ",
            nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
