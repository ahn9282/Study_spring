package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.Member;
import study.jpa.start.jpamain.jpabook.doamin.Order;
import study.jpa.start.jpamain.jpabook.doamin.OrderItem;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.util.List;

public class JpaBookMain2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            User member = new User();
            member.setName("memberAAA");

            em.persist(member);

            Team team = new Team();
            team.setName("teamAAA");
            team.getMembers().add(member);

            em.persist(team);
            em.flush();
            em.clear();

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        }
        em.close();
        emf.close();
    }
}
