package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.Child;
import study.jpa.start.jpamain.jpabook.doamin.Parent;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.util.List;

public class JpaBookMain7 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Child child1 = new Child();
            child1.setName("firstChild");
            Child child2 = new Child();
            child2.setName("secondChild");

            Parent parent = new Parent();
            parent.setName("parentAA");
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);//cascade.ALL 로 인해 parent만 persist 시 연쇄되어 같이 persist 됨

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            List<Child> childList = findParent.getChildList();
            for (Child child : childList) {
                System.out.println("child = " + child);
            }
            em.remove(findParent);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        em.close();
        emf.close();
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
