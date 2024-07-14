package itemservice;

import itemservice.domain.item.Item;
import itemservice.domain.item.ItemRepository;
import itemservice.domain.member.Member;
import itemservice.domain.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 15));

        Member member = new Member();
        member.setLoginId("test");
        member.setName("준섭");
        member.setPassword("1234!");
        memberRepository.save(member);

    }
}
