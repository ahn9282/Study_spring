package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceCallBack;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Controller + @RequestBody
@RestController
public class OrderControllerv5 {


    private final OrderServicev5 orderService;
    private final TraceTemplate template;

    public OrderControllerv5(OrderServicev5 orderService, LogTrace logTrace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(logTrace);

    }


    @GetMapping("/v5/request")
    public String request(String itemId) {
       return template.execute("OrderController.request()", () -> {
           orderService.orderItem(itemId);
           return "ok";
       });
    }

}
