package hello.advanced.app.v4;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    //private final HelloTraceV2 trace;
    private final LogTrace trace;


    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
            //void의 경우에도 Void로 객체로 변환한 타입으로 해줘야 한다.
            //ex) Integer, Double 처럼
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
                //언어 상 객체로서 의 한계로 null을 넣는다.
            }
        };
        template.execute("OrderService.request()");
    }
}
