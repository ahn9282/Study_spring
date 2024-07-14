package study.jpa.basic.jpamain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("js");
//            System.out.println("member = " + member);
//
//            em.persist(member);
//            Member member1 = em.find(Member.class,1L);
//            System.out.println("member1 = " + member1);
//
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member m : result) {
//                System.out.println("member = " + m);
//            }
//
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        }
//        em.close();
//        emf.close();
    //}
}
