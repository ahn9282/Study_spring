package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;


/*
* 체크 예외의 경우 인터페잇 활용 시
* 인터페이스에도 예외를 처리해줘야 한다.
* 이러한 경우 인터페이스가 예외가 해당하는 라이브러리 등에
* 강젝적으로 종속됨이 나타난다는 문제가 생긴다.
* 그래서 해당 MemberRepositoryEX 인터페이스는 옳지 않다.
* */
public interface MemberRepositoryEX {

    Member save(Member member) throws SQLException;

    Member findById(String memberId) throws SQLException;

    void update(String memberId, int money) throws SQLException;

    void delete(String memberId) throws SQLException;

}
