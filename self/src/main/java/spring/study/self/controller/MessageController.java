package spring.study.self.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/basic")
public class MessageController {

    @RequestMapping("/items")
    public String itemMange_basic() {
        return "items.html";
    }
}
