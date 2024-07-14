package study.jpa.start.jpamain.jpabook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.start.jpamain.jpabook.doamin.Book;
import study.jpa.start.jpamain.jpabook.doamin.User;

import java.time.LocalDateTime;

public class JpaBookMain4 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            User member = new User();
            member.setCreateBy("AN");
            member.setLastModifiedBy("KIM");
            member.setCreatedDate(LocalDateTime.now());
            member.setLastModifiedDate(LocalDateTime.now());
            em.persist(member);

            Book book = new Book();
            book.setName("QWE");
            book.setAuthor("안준섭");
            em.persist(book);
            em.flush();//영속성 컨텍스트 내 데이터를 db로
            em.clear();//현재 영속성 컨텍스트 clear

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        }
        em.close();
        emf.close();
    }
}
