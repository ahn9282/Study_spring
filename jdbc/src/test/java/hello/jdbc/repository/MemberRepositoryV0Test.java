package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member v0 = new Member("V0", 10000);
        repositoryV0.save(v0);

    }
}