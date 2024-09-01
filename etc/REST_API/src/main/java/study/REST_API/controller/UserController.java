package study.REST_API.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import study.REST_API.domain.User;
import study.REST_API.exception.UserNotFoundException;
import study.REST_API.service.UserDaoService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveAllUsers(@PathVariable("id") int id) {
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }
        return user;
    }

//    @PostMapping("/users")
//    public ResponseEntity<User> joinUser(User user) {
//        User newUser = service.save(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//    }
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        User deletedUser = service.deleteById(id);
        if(deletedUser == null) throw new UserNotFoundException("id : " + id+" : Not Exist");
        return ResponseEntity.status(HttpStatus.OK).body(deletedUser);
    }
}
