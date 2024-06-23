package hello.jpa.study2.repository;

import hello.jpa.study2.domain.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {//처음 등록이기에 id 가 null 임
            em.persist(item);
        }else{//이미 존재하는 것을 save
            em.merge(item);
            //merge 는 병합으로 변경할 때 사용(수정)
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}