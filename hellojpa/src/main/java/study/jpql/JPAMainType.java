package study.jpql;

import jakarta.persistence.*;

import java.util.List;

//프로젝션 : SELECT 절에 조회할 대상을 지정하는 것을 의미한디.
public class JPAMainType {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team team = new Team();
            team.setName("teamS");
            em.persist(team);


            Member member = new Member();
            member.setUsername("teamS");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            member.setTeam(team);
            team.getMembers().add(member);

            em.flush();
            em.clear();
            String sql = "select m.username, 'HELLO', true FRom Member m where m.type = :userType";

            List<Object[]> resultList = em.createQuery(sql)
                    .setParameter("userType", MemberType.USER)
                    .getResultList();
            for (Object[] objects : resultList) {
                System.out.println("objects = " + objects[0]+ "  " + objects[1] + "  " + objects[2]);
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

