package study.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

//JPA 표준 스펙에서는 서브쿼리는 WHERE, HAVING 절에서만 서브쿼리 사용 가능
//하이버 네이트에서는 SELECT 까지는 지원하나 FROM 절에서는 서브쿼리 사용이 쉽지 않다.
public class JPAMainFetchJoin {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team1 = new Team();
            team1.setName("팀A");
            em.persist(team1);
            Team team2 = new Team();
            team2.setName("팀B");
            em.persist(team2);
            Team team3 = new Team();
            team3.setName("팀C");
            em.persist(team3);

            Member member1 = new Member();
            member1.setUsername("User1");
            member1.setTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("User2");
            member2.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("User3");
            member3.setTeam(team2);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("User4");
            member4.setTeam(team3);
            em.persist(member4);

            em.flush();
            em.clear();

            //1 + N 을 막기 위한 유일한 방법 즉시로딩, 지연로딩이 아닌
            //join 을 통해 이에 해당하는 값들을 같이 가져오도록 하여 쿼리 한번으로 해결한다.
            //실제 Team 엔티티가 Member 엔티티에 담긴채로 반환시킨다.
            String sql = "select m from Member m join fetch m.team";
            List<Member> members = em.createQuery(sql, Member.class)
                    .getResultList();

            for (Object member : members) {
                System.out.println("member = " + member);
            }

            //컬렉션 패치 조인
            //해당 경우에는 데이터가 뻥튀기 될 가능성이 이따.마이바티스 resultMap처럼 여러 row로 반환되기 때문에
            //일대다에서 일에서 다를 끌어올 경우 데이터 뻥튀기로 자원소모가 생긴다.
            String sql2 = "select t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(sql2, Team.class)
                    .getResultList();

            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + "|| members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("member's member = " + member);
                }
            }
            // 이 경우 jpql 의 distinct 를 이용한다. jpql에서 distinct는 sql에 distinct 추가와
            //애플리케이션에서 엔티티 중복도 제거해주는 기능을 갖추고 있다.
            String sql3 = "select distinct t from Team t join fetch t.members";
            List<Team> result3 = em.createQuery(sql3, Team.class)
                    .getResultList();
            System.out.println("result3 = " + result3.size());
            for (Team team : result3) {
                System.out.println("team.getName() =" + team.getName() + "| members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println(" ->  member = " + member);
                }
            }
            //일반 조인과 페치 조인의 차이
            // 일반 조인 실행 시 연관 관계의 엔티티를 함꼐 조회하지 않고 추가의 쿼리문을 실행

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
        em.close();
        emf.close();
    }
}

