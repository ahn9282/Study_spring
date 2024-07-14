package hellow.springMVC.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users")
public class MappinClassController {

    /*
     *회원 목록 조회 : GET     /users
     * 회원 등록:    POST        /users
     * 호원 조회 :     GET       /users/{userId}
     * 회원 수정 :     PATCH     /users/{userId}
     * 회원 삭제 :     DELETE        /users/{userId}
     * */

    @GetMapping
    public String user() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return " add User";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable("userId") String userId) {
        return "get userId : " + userId;
    }


    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable("userId") String userId){
        return "update User = " + userId;
    }
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        return "delete User = " + userId;
    }
}
