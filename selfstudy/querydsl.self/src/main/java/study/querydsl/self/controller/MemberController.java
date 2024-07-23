package study.querydsl.self.controller;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.self.dto.MemberSearchCondition;
import study.querydsl.self.dto.MemberTeamDto;
import study.querydsl.self.entity.Member;
import study.querydsl.self.entity.Team;
import study.querydsl.self.repository.MemberJpaRepository;
import study.querydsl.self.repository.MemberRepository;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.searchPageSimple(condition, pageable);
    }

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV3(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.searchPageComplex(condition, pageable);
    }

    @GetMapping("/v4/members")
    public Slice<MemberTeamDto> searchMemberV4(MemberSearchCondition condition) {
        return memberRepository.searchSliceDto(condition);
    }

    @GetMapping("/v5/members")
    public Slice<MemberTeamDto> searchMemberV5(MemberSearchCondition condition, Pageable pageable) {
        Slice<MemberTeamDto> result = memberRepository.searchSliceDtoPaging(condition, pageable);
        log.info("pageable : {}", pageable);
        log.info("hasNext : {}", result.hasNext());
        return result;
    }

    @GetMapping("/members")
    @Transactional
    public ResponseEntity<Integer> getMembers() {
        int value = 0;
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);
        for (int i = 0; i < 100; i++) {
            Team selectedTeam = i % 2 == 0 ? teamA : teamB;
            em.persist(new Member("member" + i, i, selectedTeam));
            value++;
        }

        return ResponseEntity.ok(value);
    }
}
