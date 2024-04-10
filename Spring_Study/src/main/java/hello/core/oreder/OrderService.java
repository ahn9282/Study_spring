package hello.core.oreder;

public interface OrderService {
    Order createOrder(Long memberId, String itmName, int itemPrice);
}
