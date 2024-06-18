package study.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

//JPA 표준 스펙에서는 서브쿼리는 WHERE, HAVING 절에서만 서브쿼리 사용 가능
//하이버 네이트에서는 SELECT 까지는 지원하나 FROM 절에서는 서브쿼리 사용이 쉽지 않다.
public class JPAMainFetchJoin2 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {



            em.flush();
            em.clear();

            //일대다 관계 페치 조인의 경우 페이징은 성능 상 위험하다
            // 해당 경우는 db가 아닌 메모리에서 모든 데이터를 가져와 페이징을 처리한다.
            String sql2 = "select t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(sql2, Team.class)
                    .setFirstResult(0 )
                    .setMaxResults(5)
                    .getResultList();

            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + "|| members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println(" -> member's member = " + member);
                }
            }
            String sql3 = "select t from Team t ";
            List<Team> result2 = em.createQuery(sql3, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();
            System.out.println("result2 = " + result2.size());

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        em.close();
        emf.close();
    }
}

