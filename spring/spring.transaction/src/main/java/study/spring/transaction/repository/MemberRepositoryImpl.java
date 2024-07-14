package study.spring.transaction.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import study.spring.transaction.domain.Member;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Member saveMemberTx(Member member) {
        log.info("멤버 저장");
        em.persist(member);

        return member;
    }
}
