package study.jpql;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

//JPA 표준 스펙에서는 서브쿼리는 WHERE, HAVING 절에서만 서브쿼리 사용 가능
//하이버 네이트에서는 SELECT 까지는 지원하나 FROM 절에서는 서브쿼리 사용이 쉽지 않다.
public class JPAMainSubQuery {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //이런 경우 묵시적 join 이 실행된다. 왠만해선 권장되지는 않는다.
            // JPA는 버퍼로 쿼리를 실행하기에 쿼리문 개수에 마이바티스 처럼 신경을 쓸 정도까진 아니다.
           // String sql = "select m.team.name from Member m";
            //String sql = "select t.members from Team t";
            //명시적 조인을 해야한다.
            //쿼리 튜닝에 성능 상 에러사항이 많이 발생한ㄷ. 명시적 조인을 왠만해선 하자
            //프로젝션을 고려할 경우 묵시적 조인을 해야ㅕ하낟. 왜냐하면 묵시적 조인의 경우 Collection 으로 반환하기 때문
            String sql = "select m from Team t join t.members m";
            List members = em.createQuery(sql, List.class)
                    .getResultList();
            for (Object member : members) {
                System.out.println("member = " + member);
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        em.close();
        emf.close();
    }
}

