package study.jpql;

import jakarta.persistence.*;

import java.util.List;

public class JPAMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

//            Team team = new Team();
//            team.setName("team");
//            em.persist(team);
            Team team = em.find(Team.class, 1L);


            Member member = new Member();
            member.setUsername("memberB");
            member.setTeam(team);
            member.setAge(49);
            em.persist(member);
            team.getMembers().add(member);

            Query  query = em.createQuery("select m from Member m where m.username = 'memberA'");//타입이 명확하지 않거나 없을 경우 Query 사용
            //타입이 명확한 경우 TypeQuery 사용

            TypedQuery<Member> typeQuery = em.createQuery("select m from Member m where m.username = :username", Member.class)
            .setParameter("username", "memberA");//parameter 쿼리문에 넣기 :parameter 이름 방식
            //위 둘은 쿼리문 실행
            Object singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult);//결과가 정확히 하나, 단일 객체 반환 꼭 한개가 있어야함
            //없거나 둘 이상일 겨우 전용 exception 발생 -> 결과가 없을 경우에도 exception 발생에 논란이 있어 대부분 getResultList() 사용 권장

            //Collection 으로 쿼리를 받을 경우 ->  다수용
            List<Member> members = em.createQuery("select m from Member m ", Member.class)
                    .getResultList();
            for (Member m : members) {
                System.out.println("member = " + m);
            }




            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        }
        em.close();
        emf.close();
    }
    }

