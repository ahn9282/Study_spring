package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.Movie;
import study.jpa.start.jpamain.jpabook.doamin.Team;
import study.jpa.start.jpamain.jpabook.doamin.User;

public class JpaBookMain3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Movie movie = new Movie();
            movie.setDirector("준섭");
            movie.setActor("안준섭");
            movie.setName("영화A");
            movie.setPrice(15000);
            em.persist(movie);

            em.flush();//영속성 컨텍스트 내 데이터를 db로
            em.clear();//현재 영속성 컨텍스트 clear

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        }
        em.close();
        emf.close();
    }
}
