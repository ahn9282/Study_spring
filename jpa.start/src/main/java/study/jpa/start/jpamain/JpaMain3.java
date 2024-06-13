package study.jpa.start.jpamain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;

public class JpaMain3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member2 member = new Member2();
            member.setUsername("test1");

            Member2 member2 = new Member2();
            member2.setUsername("test2");

            Member2 member3 = new Member2();
            member3.setUsername("test3");

            System.out.println("==================================");
            em.persist(member);
            em.persist(member2);
            em.persist(member3);
            System.out.println("==================================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
