package study.jpql;

import jakarta.persistence.*;

import java.util.List;

//프로젝션 : SELECT 절에 조회할 대상을 지정하는 것을 의미한디.
public class JPAMainPaging {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            //paging -> 시작 점과 시작점으로 부터 개수
            List<Member> members = em.createQuery("select m from Member m order by m.age asc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(7)
                    .getResultList();
            System.out.println("Size" + members.size());
            for (Member member : members) {
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

