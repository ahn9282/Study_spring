package study.querydsl.self.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.querydsl.self.dto.MemberSearchCondition;
import study.querydsl.self.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {


    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
