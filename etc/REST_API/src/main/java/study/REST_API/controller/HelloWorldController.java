package study.REST_API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.REST_API.etc.HelloWorldBean;

@RestController
public class HelloWorldController {
    @GetMapping(path = "/hello-world")
    public String helloworld() {
        return "Hello World!!";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }
}
