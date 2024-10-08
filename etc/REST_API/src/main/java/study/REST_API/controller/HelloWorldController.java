package study.REST_API.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import study.REST_API.domain.User;
import study.REST_API.etc.HelloWorldBean;
import study.REST_API.exception.UserNotFoundException;
import study.REST_API.service.UserDaoService;

import java.net.URI;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {
    private  final UserDaoService service;
    private  final MessageSource messageSource;


    @GetMapping(path = "/hello-world")
    public String helloworld() {
        return "Hello World!!";
    }

    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloworldBeanPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false)Locale locale){
        return messageSource.getMessage("greeting.message", null, locale);
    }

//    @GetMapping("/hello-world-bean")
//    public HelloWorldBean helloWorldBean() {
//        return new HelloWorldBean("Hello World");
//    }
//
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User savedUser = service.save(user);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedUser.getId())
//                .toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @GetMapping("/users/{id}")
//    public User retrieveUser(@PathVariable int id){
//        User user = service.findOne(id);
//        if(user == null){
//            throw new UserNotFoundException("id : " + id+" >> Not Exist");
//        }
//        return user;
//    }
}
