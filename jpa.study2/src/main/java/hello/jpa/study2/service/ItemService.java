package hello.jpa.study2.service;

import hello.jpa.study2.Repository.ItemRepository;
import hello.jpa.study2.domain.item.Book;
import hello.jpa.study2.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    //수정1 - 변경 감지 기능 사용
    //대충 필드 값만 setter 하는 개념
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        //해당 방법은 파라미터로 넘어온 준영속 상태의 엔티티를 토대로
        //영속성 컨텍스트에서 엔티티를 다시 조횐한 후 데이터를 수정
        //현재 @Transactional 로인해 영속성 상태에 있기에 현재 영속성 컨텍스트 내에서 엔티티 값을 수정하는 방식
    }

    //수정2 - 병합(merge)
    //파라미터로 넘어온 주역속 엔티티의 식별자 값으로 1차 캐시에서 엔티티를 조회한다.
    //그리고 set을 통해 기존 값을 새 값으로 밀어버린다(바꿔줌) 그 후 반환
    //선언하는 개념
    //변경 감지의 경우 원하는 속성만 선택해 변경이 가능하다. 그러나 병합은 모든 소성을 변경(밀어버린다 보면 된다.) 잘못쓰면 큰 문제를 유발할 가능성이 무조건 있다.
    //대충 put과 petch의 차이?

    @Transactional
    public void saveItem(Book item) {
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }



}
