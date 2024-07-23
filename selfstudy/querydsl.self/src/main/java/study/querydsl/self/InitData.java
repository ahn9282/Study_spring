package study.querydsl.self;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.self.entity.Member;
import study.querydsl.self.entity.Team;
import study.querydsl.self.repository.MemberRepository;


@Component
public class InitData {

    private final InitService initService;


    public InitData( InitService initService) {
        this.initService = initService;
    }

    @PostConstruct
    public void init() {
        initService.dbInit1();

    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;
        private final MemberRepository memberRepository;

        private int randomAge(){
            int result = (int) (Math.random() * 60) + 15;
            return result;
        }


        public void dbInit1() {
            Team team1 = new Team("teamA");
            Team team2 = new Team("teamB");
            em.persist(team1);
            em.persist(team2);

            for (int i = 0; i < 100; i++) {
                Member member = new Member("user" + i, randomAge(), (i % 2 == 1) ? team1 : team2);
                memberRepository.save(member);
            }
        }
    }
}
