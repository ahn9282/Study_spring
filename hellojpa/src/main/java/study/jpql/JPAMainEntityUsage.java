package study.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

//프로젝션 : SELECT 절에 조회할 대상을 지정하는 것을 의미한디.
public class JPAMainJoin {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            List<Member> innerJoinResult = em.createQuery("select m FROM Member m left join m.team t where t.name = :teamName", Member.class)
                    .setParameter("teamName","teamB")
                    .getResultList();
            for (Member member : innerJoinResult) {
                System.out.println("member = " + member);
            }

            List<Member> crossJoinResult = em.createQuery("select m FROM Member m , Team t  where t.id = m.team.id", Member.class)

                    .getResultList();

            for (Member member : crossJoinResult) {
                System.out.println("member = " + member);
            }

            //조인 대상 필터링 ON
            String sql = "select m FROM Member m join Team t on m.team.name='teamA'";
            List<Member> joinFilterResult = em.createQuery(sql, Member.class)
                    .getResultList();
            for (Member member : joinFilterResult) {
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

