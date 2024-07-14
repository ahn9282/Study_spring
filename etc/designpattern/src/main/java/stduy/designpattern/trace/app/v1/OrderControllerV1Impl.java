package stduy.designpattern.trace.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
public class OrderControllerV1Impl implements OrderControllerV1{

    private final OrderServiceV1 orderService;

    @GetMapping("/v1/request")
    public String request(@RequestParam("itemId") String itemId) {
                orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v1/no-log")
    public String noLog() {
        return "noLog";
    }
}
