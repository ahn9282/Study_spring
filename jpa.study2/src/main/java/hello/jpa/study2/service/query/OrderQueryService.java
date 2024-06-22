package hello.jpa.study2.service.query;

import hello.jpa.study2.api.OrderApiController;
import hello.jpa.study2.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderRepository orderRepository;

    public List<OrderApiController.OrderDto> ordersV3() {
        return orderRepository.findAllWithMemberDelivery().stream()
                .map(OrderApiController.OrderDto::new)
                .collect(toList());
    }
}
