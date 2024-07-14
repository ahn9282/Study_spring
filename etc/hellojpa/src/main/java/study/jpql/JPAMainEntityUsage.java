package study.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

//프로젝션 : SELECT 절에 조회할 대상을 지정하는 것을 의미한디.
public class JPAMainEntityUsage {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            String sql = "select m from Member m where m.id =:memberId";
            Member findMember1 = em.createQuery(sql, Member.class)
                    .setParameter("memberId", em.find(Member.class,1L).getId())
                    .getSingleResult();

            System.out.println("findMember1 = " + findMember1);
            //엔티티는 기본키를 기반으로 하여 이 둘은 결과가 같다.
            String sql2 = "select m from Member m where m =:member";
            Member findMember2 = em.createQuery(sql2, Member.class)
                    .setParameter("member", em.find(Member.class,1L))
                    .getSingleResult();

            System.out.println("findMember2 = " + findMember2);

            Team team1 = em.find(Team.class, 1L);
            String sql3 = "select m from Member m where m.team = :team";
            List result3 = em.createQuery(sql3)
                    .setParameter("team", team1)
                    .getResultList();
            for (Object o : result3) {
                System.out.println("team = " + o);
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

