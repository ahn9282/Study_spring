package study.spring.transaction.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import study.spring.transaction.domain.Member;

public interface MemberRepositoryCustom {

    Member saveMemberTx(Member member);

}
