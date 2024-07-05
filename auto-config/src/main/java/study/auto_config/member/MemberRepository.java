package study.auto_config.member;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    public final JdbcTemplate template;

    public void initTable(){
        template.execute("create table member(member_id varchar primary key, name varchar)");
    }

    public void save(Member member) {
        template.update("insert into member values (?,?)",
                            member.getMemberId(),
                              member.getName());
    }
    public Member find(String memberId) {
        return template.queryForObject("select member_id, name from member where member_id = ?",
                BeanPropertyRowMapper.newInstance(Member.class),//새 Member 인스턴스로 반환하도록 하는 설정
        memberId);

    }
    public List<Member> findAll(){
        return template.query("select * from member",
                BeanPropertyRowMapper.newInstance(Member.class));
    }
}
