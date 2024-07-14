package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.Address;
import study.jpa.start.jpamain.jpabook.doamin.AddressEntity;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.util.Set;

public class JpaBookMain9 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            User member = new User();
            member.setName("memberA");
            member.setHomeAddress(new Address("home", "street1", "123"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("돈까스");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1", "street1", "123"));
            member.getAddressHistory().add(new AddressEntity("old2", "street2", "123"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=================START=======================");
            User findMember = em.find(User.class, member.getId());
            //값 컬렉션의 경우 지연로딩이 default 이므로 호출 시에만 조회 쿼리문이 생성되어 보내진다.
            Set<String> foods = findMember.getFavoriteFoods();
            for (String food : foods) {
                System.out.print(" \t food = " + food);
            }
                System.out.println();
            Address homeAddress = findMember.getHomeAddress();
            //값 타입의 경우 이렇게 setter가 아닌 갈아끼워주는 방식으로 해야만 사이드 이펙트 오류에서 안전하다.
            findMember.setHomeAddress (new Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("족발");

            findMember.getAddressHistory().remove(new AddressEntity("old2", "street2", "123"));
            findMember.getAddressHistory().add(new AddressEntity("new City1", "newStreet", "10000"));

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
