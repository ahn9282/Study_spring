package study.REST_API.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import study.REST_API.domain.User;
import study.REST_API.exception.UserNotFoundException;
import study.REST_API.service.UserDaoService;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Tag(name="user-controller", description = "일반 사용자 서비스 컨트롤러")
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @Operation(summary="사용자 정보조회 API",description = "사용자 ID를 이용해서 사용자 상세 정보 조회를 합니다.")
    @ApiResponses(
            {
            @ApiResponse(responseCode="200",description = "OK!!"),
            @ApiResponse(responseCode="400",description = "BAD REQUEST !!"),
            @ApiResponse(responseCode="404",description = "USER NOT FOUND!!"),
            @ApiResponse(responseCode="500",description = "INTERNAL SERVER ERROR!!"),
        }
    )
    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@Parameter(description = "사용자 ID",required = true, example = "1") @PathVariable("id") int id) {
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException("id : " + id+" >> Not Exist");
        }

        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo.withRel("all-users"));// all-users : http://localhost:9282/users
        return entityModel;
    }

//    @GetMapping("/users/{id}")
//    public User retrieveAllUsers(@PathVariable("id") int id) {
//        User user = service.findOne(id);
//
//        if(user == null){
//            throw new UserNotFoundException("id : " + id+" >> Not Exist");
//        }
//        return user;
//    }

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
