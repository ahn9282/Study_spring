package hello.jdbc.exception.translator;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.EX.MyDBException;
import hello.jdbc.repository.EX.MyDuplicateKeyException;
import hello.jdbc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ExTranslatorV1TestTest {

    @Slf4j
    @RequiredArgsConstructor
    static class Service{

        private final MemberRepository repository;

        public void create(String memberId) {
            try {
                repository.save(new Member(memberId, 0));
                log.info("saveId = {}", memberId);
            }catch(MyDuplicateKeyException e){

                log.info("키 중복, 복구를 시도하겠습니다.");
                String retryId = generateNewId(memberId);
                log.info("retryId = {}", retryId);

                repository.save(new Member(retryId, 0));

            }catch(MyDBException e){
                log.info("데이터 접근 계층 예외", e);
                throw e;

            }
        }

        private String generateNewId(String memberId){
            return memberId + new Random().nextInt(10000);
        }

    }

}