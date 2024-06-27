package study.jpa.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.jpa.domain.Item;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return
        em.find(Item.class, id);
    }
    public List<Item> findAllItems(){
        return em.createQuery("select i from Item i ", Item.class)
                .getResultList();
    }

}
