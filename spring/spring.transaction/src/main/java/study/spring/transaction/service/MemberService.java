package study.spring.transaction.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.spring.transaction.domain.Log;
import study.spring.transaction.domain.Member;
import study.spring.transaction.repository.LogRepository;
import study.spring.transaction.repository.MemberRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;


    @Transactional
    public void joinV1(String username) {
        Member member = new Member(username);
        Log record = new Log(username);

        log.info("==== memberRepository 호출 시작 ====");
        memberRepository.saveMemberTx(member);
        log.info("==== memberRepository 호출 종료 ====");

        log.info("==== LogRepository 호출 시작 ====");
        logRepository.saveLogTx(record);
        log.info("==== LogRepository 호출 종료  ====");
    }

    @Transactional
    public void joinV2(String username) {
        Member member = new Member(username);
        Log record = new Log(username);

        log.info("==== memberRepository 호출 시작 ====");
        memberRepository.saveMemberTx(member);
        log.info("==== memberRepository 호출 종료 ====");

        log.info("==== LogRepository 호출 시작 ====");
        try{
            logRepository.saveLogTx(record);
        }catch(Exception e){
            log.info("log 저장에 실패했습니다. logRecord = {}", record.getMessage());
            log.info("정상 흐름 반환");
        }

        log.info("==== LogRepository 호출 종료  ====");
    }
}
