package stduy.designpattern.trace.slslsl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Repository
@Data
@Slf4j
public class FakeLogic {


    public FakeLogic() {
    }

    public Integer process(int a) {
        log.info("로직 수행 중~~  "+a+" 받음 1 더하는 중");
        return a + 1;
    }


}
