package study.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

//JPA 표준 스펙에서는 서브쿼리는 WHERE, HAVING 절에서만 서브쿼리 사용 가능
//하이버 네이트에서는 SELECT 까지는 지원하나 FROM 절에서는 서브쿼리 사용이 쉽지 않다.
public class JPAMainSubQuery {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {


            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        em.close();
        emf.close();
    }
}

