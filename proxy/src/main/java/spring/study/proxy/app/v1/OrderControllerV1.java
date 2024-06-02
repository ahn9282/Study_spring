package spring.study.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

// @Controller 만이 아닌 @RequestMapping 어노테이션도 컨트롤러로 인식
// 스프링은  @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식한다.
@RestController
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();

}
