package study.hello.reactor.hello.reactor;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InitTest {


   private  List<String> list = new ArrayList<>();

    public InitTest() {
        for (int  i = 0; i < 50; i++) {
            list.add("member" + i);
        }
    }
}
