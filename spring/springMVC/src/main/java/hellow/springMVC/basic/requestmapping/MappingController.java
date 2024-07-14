package hellow.springMVC.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    @GetMapping(value="/mapping-get-v2")
    public String mappingGetV2(){
        log.info("mapping-get-v2");
        return "OK2";
    }
 @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data){
     log.info("mappingPath userId={}", data);
     return "ok3";

     /**
      * @RequestParam 사용
      * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
      */
    } @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable("userId") String data, @PathVariable("orderId") Long orderId){
     log.info("mappingPath userId={}, orderId = {}",data, orderId);
     return "ok4";
    }

    //특정 파라미터 정보까지 있을 때 요청이 성립
    //params = {"mode=debug","data=good"}  -> ?mode=good으로 가능
    @GetMapping(value= "/mapping-param", params = "mode=debug")
public String mappingParam(){
        log.info("mappingParam");
        return "ok5";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value= "/mapping-header", params = "mode=debug")
public String mappingHeader(){
        log.info("mappingHeader");
        return "ok5";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */

    @PostMapping(value="/mapping-consume", consumes="application/json")
    public String mappingConsumes(){                // 'MediaType.APPLICATION_JSON_VALUE'가 공식으로 지원해주는
        log.info("mappingConsumes");                //상수가 존재
        return "ok6";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value="/mapping-produce", consumes= MediaType.TEXT_HTML_VALUE)
    public String mappingProduces(){                    //text.html
        log.info("mappingProduces");
        return "ok7";
    }


}
