package hello.itemservice.domain;

import hello.itemservice.config.SpringDataJpaConfig;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import hello.itemservice.repository.memory.MemoryItemRepository;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@Slf4j
@Transactional
@SpringBootTest
 class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;


    /*
    *transactionManager를 이용하여 afterEach, BeforeEach 마다 트랜잭션을 시작, 롤백을
    *  DB에 접근하는 test들이 각자 격리(?),  독립(?)성을 가진다.
    *  */
  /*  @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;*/
    @BeforeEach
    void beforeEach(){
       /* //트랜잭션 시작
         status = transactionManager.getTransaction(new DefaultTransactionDefinition());*/
    }


    @AfterEach
    void afterEach() {

 /*       //트랜잭션 롤백
        transactionManager.rollback(status);*/

    }

    @Test
    void save() {
            //given
        Item item = new Item("itemA", 10000, 10);
            //when
        Item savedItem = itemRepository.save(item);
            //then
        Item findItem = itemRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void updateItem() {
            //given
        Item item = new Item("itemT1", 15000, 17);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();
            //when
        ItemUpdateDto updateParam = new ItemUpdateDto("itemT2", 25000, 37);
        itemRepository.update(itemId, updateParam);
            //then
        Item findItem = itemRepository.findById(itemId).get();
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }

    @Test
    void findItems() {
            //given
        Item item1 = new Item("itemA-1", 10000, 10);
        Item item2 = new Item("itemA-2", 20000, 20);
        Item item3 = new Item("itemB-1", 30000, 30);

        log.info("repository = {}", itemRepository.getClass());
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        //둘 다 없음 검증
        //test(null, null, item1, item2, item3);
        test("", null, item1, item2, item3);
            //itemName 검증
        test("itemA", null, item1, item2);
        test("itemA", null, item1, item2);
        test("itemB", null, item3);
        //maxPrice 검증
        test(null, 10000, item1);
            //둘 다 있음 검증
        test("itemA", 10000, item1);
    }

    void test(String itemName, Integer maxPrice, Item... items) {
        List<Item> result = itemRepository.findAll(new ItemSearchCond(itemName,
                maxPrice));
        assertThat(result).containsExactly(items);
    }
}