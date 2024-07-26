
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import study.hello.reactor.hello.reactor.InitTest;


@Slf4j
public class FromStream {

    public static void main(String[] args) {

        InitTest t = new InitTest();
        Flux
                .fromStream(() -> t.getList().stream())
                .filter(member -> member.equals("member1") || member.equals("member5"))
                .subscribe(data -> log.info("{}", data));
    }
}
