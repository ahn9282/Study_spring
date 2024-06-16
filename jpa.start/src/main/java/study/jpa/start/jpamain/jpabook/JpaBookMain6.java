package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.util.List;

public class JpaBookMain6 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            User mem1 = em.find(User.class, 1L);
            Team team1 = em.find(Team.class, mem1.getTeam().getId());

            em.flush();//영속성 컨텍스트 내 데이터를 db로
            em.clear();//현재 영속성 컨텍스트 clear

            System.out.println("mem1 = " + mem1.getTeam().getClass());

            System.out.println("============================================");
            System.out.println("teamName = " + mem1.getTeam().getName());
            System.out.println("============================================");

            //jpql from 절에는 table 명이 아닌 Entity 명
            //FetchType=EAGER 경우 UserEntity 의 테이블을 조회 하는 동시에 즉시 teamEntity 테이블도 조회하게 된다.
            // -> 계속 같이 즉시 조회되서 자원낭비
            List<User> members = em.createQuery("select m from User m", User.class)
                    .getResultList();


            for (User member : members) {
                System.out.print("member = " + member + "\t");
            }
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
