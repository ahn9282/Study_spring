package study.spring.data_jpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.spring.data_jpa.entity.Team;

import java.util.List;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager em;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    public void delete(Team team) {
        em.remove(team);

    }

    public List<Team> findAll(){
      return   em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    public Team findById(Long id) {
        Team team = em.find(Team.class, id);
        return team;
    }
    public long count() {
       return  em.createQuery("select count(t) from Team t", Long.class)
                .getSingleResult();
    }
}
