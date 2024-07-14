package stduy.designpattern.trace.app.v1;

import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
