package ba.unsa.etf.pnwt.petcenter.Controller;


import ba.unsa.etf.pnwt.petcenter.Model.User;
import ba.unsa.etf.pnwt.petcenter.Model.UserResponse;
import ba.unsa.etf.pnwt.petcenter.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/user/{username}/role/{name}")
    public ResponseEntity<User> addRoleToUser(@PathVariable String username, @PathVariable String name) {
        return new ResponseEntity<User>(userService.addRoleToUser(username, name), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

}
