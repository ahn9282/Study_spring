package study.REST_API.controller;


import com.ctc.wstx.shaded.msv_core.util.Uri;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import study.REST_API.repository.PostRepository;
import study.REST_API.service.UserService;
import study.REST_API.domain.Post;
import study.REST_API.domain.User;
import study.REST_API.dto.AllUserResearchDto;
import study.REST_API.exception.UserNotFoundException;
import study.REST_API.repository.UserRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, UserService userService, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Transactional
    @GetMapping("/users")
    public AllUserResearchDto retrieveAllUsers() {
        AllUserResearchDto data = new AllUserResearchDto();
        data.setUsers(userRepository.findAll());
        data.setTotal((int) userRepository.count());
        System.out.println(data);
        return data;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("id = " + id);
        }

        EntityModel entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        User save = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        return userService.getPostsByUserId(id);
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("id = " + id);
        }
        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
