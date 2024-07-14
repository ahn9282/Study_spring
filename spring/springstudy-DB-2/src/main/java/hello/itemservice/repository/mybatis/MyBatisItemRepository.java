package hello.itemservice.repository.mybatis;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MyBatisItemRepository implements ItemRepository {

    private final ItemMapper itemMapper;


    @Override
    public Item save(Item item) {
        itemMapper.save(item);
        log.info("itemMapper class={}", itemMapper.getClass());
        return item;
    }

    @Override
    public void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateDto) {
        itemMapper.update(id, updateDto);

    }

    @Override
    public Optional<Item> findById(Long id) {

        return itemMapper.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        return itemMapper.findAll(cond);
    }
}
