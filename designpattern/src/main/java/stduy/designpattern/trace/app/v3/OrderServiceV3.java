package stduy.designpattern.trace.app.v3;


public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    public OrderServiceV3(OrderRepositoryV3 orderRepository) {
        this.orderRepository = orderRepository;
    }
    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
