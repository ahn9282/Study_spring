package study.hello.reactor.hello.operator.transformation;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import study.hello.reactor.hello.InitTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Merge2 {
    public static void main(String[] args) throws InterruptedException {

        InitTest t = new InitTest();

        Flux    //비동기 교차 -> Flux 마다 하나의 쓰레드가 아닌 emit 마다 다른 thread 할당
                .merge(getMeltDownRecoveryMessage(t.getList()) )
                .subscribe(log::info);

        Thread.sleep(2000L);
    }

    private static List<Flux<String>> getMeltDownRecoveryMessage(List<String> list){
        List<Flux<String>> messages = new ArrayList<>();
        for (String str : list) {
            messages.add(Flux.just(str));
        }
        return messages;
        //?
    }

}
