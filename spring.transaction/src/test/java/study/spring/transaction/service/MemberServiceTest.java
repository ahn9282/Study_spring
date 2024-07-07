package study.spring.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;
import study.spring.transaction.repository.LogRepository;
import study.spring.transaction.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    LogRepository logRepository;

    @Autowired
    MemberRepository memberRepository;


    /**
     *memberService @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON
     */
    @Test
    void outerTxOff_success(){
        String username = "outerTxOff_success";

        memberService.joinV1(username);

        assertTrue(memberRepository.findByUsername(username).isPresent());
        assertTrue(logRepository.findByMessage(username).isPresent());
    }

    /**
     *memberService @Transactional : OFF
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON    exception
     */
    @Test
    void outerTxOff_fail() {
        String username = "로그예외_outerTxOff_fail";

        Assertions.assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);


        assertTrue(memberRepository.findByUsername(username).isPresent());
        assertTrue(logRepository.findByMessage(username).isEmpty());
    }
    /**
     *memberService @Transactional : ON
     * memberRepository @Transactional : OFF
     * logRepository @Transactional : OFF
     */
    @Test
    void singleTx(){
        String username = "singleTx";

        memberService.joinV1(username);

        assertTrue(memberRepository.findByUsername(username).isPresent());
        assertTrue(logRepository.findByMessage(username).isPresent());
    }

    /**
     *memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON
     */
    @Test
    void outerTxOn_success(){
        String username = "outerTxOn_success";

        memberService.joinV1(username);

        assertTrue(memberRepository.findByUsername(username).isPresent());
        assertTrue(logRepository.findByMessage(username).isPresent());
    }
    /**
     *memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON    exception
     */
    @Test
    void outerTxOn_fail() {
        String username = "로그예외_outerTxOn_fail";

        Assertions.assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);


        assertTrue(memberRepository.findByUsername(username).isEmpty());
        assertTrue(logRepository.findByMessage(username).isEmpty());
    }

    /**
     *memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON    exception
     */
    @Test
    void recoverException_fail() {
        String username = "로그예외_recoverException_fail";

        Assertions.assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);


        assertTrue(memberRepository.findByUsername(username).isEmpty());
        assertTrue(logRepository.findByMessage(username).isEmpty());
    }


    /**
     *memberService @Transactional : ON
     * memberRepository @Transactional : ON
     * logRepository @Transactional : ON    exception
     */
    @Test
    void recoverException_success() {
        String username = "로그예외_recoverException_success";

        memberService.joinV2(username);

        assertTrue(memberRepository.findByUsername(username).isPresent());
        assertTrue(logRepository.findByMessage(username).isEmpty());
    }
}