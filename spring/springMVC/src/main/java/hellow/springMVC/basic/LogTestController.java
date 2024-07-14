package hellow.springMVC.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {
   // private final Logger log = LoggerFactory.getLogger(getClass());
    // LomBok의 @Slf4j를 통해 자동으로 위 코드를 등록하여 사용할 수 있다.

    @RequestMapping("/log-test")
    public String LogTest() {
        String name="Spring";

        System.out.println("name = " + name);

        log.trace("trace my log log={}"+ name);
        //이렇게 사용 시 설정을 무시하지는 않지만 java특성 상 무시되는 코드의 연산이 이루어져 권장되지 않는다.
        //문자열 2개가 + 되는 연산이 쓸데 없이 일어난다.

        //Logger레벨 순  아래로 낮아짐
        //properties에서 설정을 통해 레벨 설정이 가능 default : Info
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
