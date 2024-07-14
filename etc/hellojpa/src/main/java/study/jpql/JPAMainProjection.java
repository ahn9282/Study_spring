package study.jpql;

import jakarta.persistence.*;

import java.util.List;

//프로젝션 : SELECT 절에 조회할 대상을 지정하는 것을 의미한디.
public class JPAMainProjection {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Member member = new Member();
            member.setUsername("memberC");
            member.setAge(14);
            em.persist(member);
            // 지금 경우에는 결과에 해당하는 entity  들이 영속성 컨텍스트에서 관리된다.
            Query query = em.createQuery("select m, m.team from Member m join m.team t ");
            List resultList = query.getResultList();
            for (Object o : resultList) {
                System.out.println("result = " + o.toString());
            }

            //이 결과를 받아야 할 경우 - 1
            List resultList1 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();
            Object o = resultList1.get(0);
            Object[] result1 = (Object[]) o;
            System.out.println(" result 1 username = " + result1[0]);
            System.out.println("age = " + result1[1]);

            //이 결과를 받아야 할 경우 - 2
            List<Object[]> resultList2 = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();
            for (Object[] objects : resultList2) {
                System.out.println("result2 = username : " + objects[0] + ", age : " + objects[1]);
            }

            //이 결과를 받아야 할 경우 - 3 - jpql 문에 new 부분을 유심히 봐야한다. 감싸려는 객체로 추가적인 처리가 있다. - 패키지도 적어야한다. - 감싸는 객체에 생성자도 필수
            //생성자 타입이랑 순서도 맞아야한다.
            List<MemberDTO> resultList3 = em.createQuery("select new study.jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            for (MemberDTO memberDTO : resultList3) {
                System.out.println("result3 = " + memberDTO);
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

