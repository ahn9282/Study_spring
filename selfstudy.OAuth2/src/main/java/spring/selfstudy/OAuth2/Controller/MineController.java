package spring.selfstudy.OAuth2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MineController {

    @GetMapping("/my")
    public String myPage(){

        return "my";
    }
}
