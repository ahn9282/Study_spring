package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.Address;
import study.jpa.start.jpamain.jpabook.doamin.AddressEntity;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.util.List;
import java.util.Set;

public class JpaJPQLMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            User member1 = new User();
            member1.setName("memberA");
            em.persist(member1);

            List<User> result = em.createQuery(
                            "select m from User m where m.name like '%member%'",
                            User.class)
                    .getResultList();
            for (User user : result) {
                System.out.println("user = " + user);
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
