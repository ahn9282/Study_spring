package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.Member;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.util.List;

public class JpaBookMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 저장 줄을 많이 먹어서 줄임
           Team team = new Team(); team.setName("TeamA"); em.persist(team);
           Team team2 = new Team(); team2.setName("TeamB"); em.persist(team2);
           User member = new User();member.setName("user1");  member.setTeam(team);em.persist(member);
           User member2 = new User(); member2.setName("User2");  member2.setTeam(team2);  em.persist(member2);

            Team team3 = new Team();
            team3.setName("TeamC");
            em.persist(team3);

           User member3 = new User();
           member3.setName("User3");
            em.persist(member3);

            team3.addMember(member3);

             em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team3.getId());
            List<User> members = findTeam.getMembers();
            for (User user : members) {
                System.out.println("user = " + user);
            }

//            User findMember = em.find(User.class, member2.getId());
//            List<User> members = findMember.getTeam().getMembers();
//
//            for (User user : members) {
//                System.out.println("user = " + user.getId()+" "+ user.getName()+" "+ user.getTeam().getId() +" " + user.getTeam().getName());
//            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        }
        em.close();
        emf.close();
    }
}
