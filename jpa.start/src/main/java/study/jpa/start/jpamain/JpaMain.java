package study.jpa.start.jpamain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

@EntityScan
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx = em1.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setName("MemberA");
            member.setRoleType(RoleType.USER);
            member.setCreatedDate(null);
            member.setLastModifiedDate(null);
            member.setDescription("연습");
            em1.persist(member);
           /* Member findMember = em1.find(Member.class, 2L);
            System.out.println("findMember = " + findMember);*/
          /*  List<Member> resultList = em1.createQuery("select m from Member as m where m.name like '%C%'", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(1)
                    .getResultList();*/
          /*  for (Member member : resultList) {
                System.out.println("member = " + member);
            }*/

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em1.close();
        }

        emf.close();
    }
}
