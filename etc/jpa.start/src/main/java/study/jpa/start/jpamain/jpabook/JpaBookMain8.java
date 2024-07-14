package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.*;

import java.util.List;

public class JpaBookMain8 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Address address = new Address("city", "street", "10101");
            Address workAddress = new Address("company", "street", "123");
            User member = new User();
            member.setName("worker1");
            member.setHomeAddress(address);
            member.setWorkAddress(workAddress);
            em.persist(member);

            User member2 = new User();
            member2.setName("worker2");
            member2.setHomeAddress(address);
            member2.setWorkAddress(workAddress);
            em.persist(member2);

            //그냥 이렇게 setter 할 경우 member와 member2 의 city 모두 바뀐다. 그래서 복사로 하여 set 해야한다.
            //공유 참조로 인한 부작용이라 한다. 불변객체로 하여 setter를 없애 이 자체를 불가능하게 한다.
           // member.getHomeAddress().setCity("newCity");

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        em.close();
        emf.close();

        String aaa = "aaa";
        String bb = aaa;
        aaa = "bbb";
        System.out.println("aaa = " + aaa);
        System.out.println("bb = " + bb);
    }

    private static void printMember(User member) {
        System.out.println("member = " + member.getName());

    }

    private static void printMemberAndTeam(User member) {
        String username = member.getName();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team);
    }
}
