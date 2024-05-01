package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/*
 * JDBC - DriverManager 사용
 * */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {

        String sql = "insert into member(member_id, money) values (?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.info("db error", e);
            throw e;
        } finally {
            try {
                close(conn, pstmt, null);
            } catch (Exception e) {
                log.info(" Exception ",e);
            }
        }
    }

    public Member findById(String memberId) throws SQLException {

        String sql = "select * from member where member_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

             conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }else{
                throw new NoSuchElementException("member not found member_ID = " + memberId);
            }
        }catch (SQLException e){
            log.info("db error", e);
            throw e;

        }finally {
        close(conn, pstmt, rs);
        }
    }

    private void close(Connection conn, Statement stmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            if (stmt != null)
                stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            log.info("error ", e);

        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}