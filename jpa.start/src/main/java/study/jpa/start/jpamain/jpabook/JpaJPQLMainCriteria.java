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

public class JpaJPQLMainCriteria {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> m = query.from(User.class);

            CriteriaQuery<User> cq = query.select(m).where(cb.equal(m.get("name"), "member1"));
            List<User> resultList = em.createQuery(cq)
                    .getResultList();

            for (User user : resultList) {
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
