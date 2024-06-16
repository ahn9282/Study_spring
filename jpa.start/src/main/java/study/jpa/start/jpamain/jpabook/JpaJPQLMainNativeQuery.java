package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.util.List;

public class JpaJPQLMainNativeQuery {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            User user = new User();
            user.setName("member123");
            em.persist(user);

            String sql = "select * from members ";
            List resultList = em.createNativeQuery(sql, User.class).getResultList();
            for (Object o : resultList) {
                System.out.println("o = " + o);
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
