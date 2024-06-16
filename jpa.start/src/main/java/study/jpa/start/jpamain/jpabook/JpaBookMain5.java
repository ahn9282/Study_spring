package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.Book;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.time.LocalDateTime;

public class JpaBookMain5 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            User member1 = new User();
            member1.setName("memberA");
            em.persist(member1);

            User member2 = new User();
            member2.setName("memberB");
            em.persist(member2);

            User member3 = new User();
            member3.setName("memberC");
            em.persist(member3);

            em.flush();//영속성 컨텍스트 내 데이터를 db로
            em.clear();//현재 영속성 컨텍스트 clear

            User m1 = em.find(User.class, member1.getId());//member1 id 값으로 찾은 User Entity
            System.out.println("m1 = " + m1.getClass());

            User m2 = em.getReference(User.class, member2.getId());//member2 id 값으로 찾은 User Entity proxy
            System.out.println("m2 = " + m2.getClass());

            User references = em.getReference(User.class, member1.getId());
            System.out.println("references = " + references.getClass());// 이 때까지는 같음

          User references2 = em.getReference(User.class, member2.getId());
            System.out.println("references2 = " + references2.getClass());
            //이도 member2 id 의 entity는 1차캐시에 없고 proxy로만 존재하기에 이도 proxy객체로 나옴

            //왜냐하면 member1 에서 이미 find로 찾아 낸 id로 통한 m1이 이미 영속성 컨텍스트(1차 캐시) 내 존재하여 이를 참조하기 때문에 proxy로 호출 하지 않음
            //그래서 m1도 find가 아닌 getReference로 할 경우 proxy 객체로 나옴
            //id가 아닌 다른 영속성 컨텍스트(1차 캐시) 내 없는 엔티티를 조회 시 proxy로 나온다.
            System.out.println("1. a == a : " + (m1 == references));//true
            System.out.println("2. a == a : " + (m1 == references));//true

            User refM3 = em.getReference(User.class, member3.getId());//1차 캐시에 없기에 proxy
             System.out.println("refM3 = " + refM3);
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refM3));
            User findM3 = em.find(User.class, member3.getId());//1차 캐시에 올림 UserEntity
            System.out.println("findM3 = " + findM3);
            System.out.println("M3. refM3 is UserEntity? : " + (refM3 instanceof User));


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
