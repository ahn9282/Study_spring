package hello.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */
@Slf4j
public class MemberServiceV3_2 {

    // private final DataSource dataSource;
    //private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        //트랜잭션 템플릿은 트랜잭션 매니저를 파라미터로 주입을 받기 때문에 @RequiredargsConstructor를 쓰지 않는다. 따로 생성자를 선언
        this.txTemplate = new TransactionTemplate(transactionManager);
        
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //트랜잭션 템플릿을 통해 비즈니스 로직으 ㄹ제외한 다른 코드들이 현저히 줄어들었다.
        txTemplate.executeWithoutResult((status) -> {
            try {
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                //람다에서는 예외를 Throws로 던질 수 없ㄱ디 때문에 try catch로 무조건 람다 내에서 처리해준다.
                log.info("트랜잭션 템플릿에서 예외 발생", e);
                throw new RuntimeException(e);
                //SQLException은 체크 예외 이기에 이를 언체크 예외로 던져버리면 롤백이 가능하다.
            }
        });
    }

    private void bizLogic(String fromId, String toId, int money) throws
            SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);
        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
//    private void release(Connection con) {
//        if (con != null) {
//            try {
//                con.setAutoCommit(true); //커넥션 풀 고려
//                con.close();
//            } catch (Exception e) {
//                log.info("error", e);
//            }
//        }
//    }
}