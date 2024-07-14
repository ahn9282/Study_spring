package study.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

//프로젝션 : SELECT 절에 조회할 대상을 지정하는 것을 의미한디.
public class JPAMainNamedQuery {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
           em.flush();
           em.clear();

            List<Member> members1 = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "User3")
                    .getResultList();
            for (Member member : members1) {
                System.out.println("member = " + member);
            }

            //벌크 연산 - 쿼리 한 번으로 테이블의 여러 로우 값들을 변경
            //update, delete 지원
            //벌크 연산에 경우 영속성 컨텍스트를 무시하고 DB에 직접 쿼리를 실행
            int resultCount = em.createQuery("update Member m set m.age=20")
                    .executeUpdate();
            System.out.println("resultCount= " + resultCount);
            //벌크 연산 수행 후 영속성 컨텍스트를 초기화(clear ) 하여야 영속성 컨텍스트와 DB 간 비 동기화 문제가 해결
            //벌크 연산의 경우 DB에만 반영이 된다. 그래서 em.clear() 후 db에서 새로이 값을 가져와야 동기화가 된다.
            
            Member findMember1 = em.find(Member.class, 1L);
            System.out.println("findMember1 age = " + findMember1.getAge());//0 <- 기존 값
            em.clear();

            Member findMember2 = em.find(Member.class, 1L);
            System.out.println("findMember2.age = " + findMember2.getAge());//20


            tx.commit();
            
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        em.close();
        emf.close();
    }
    }

