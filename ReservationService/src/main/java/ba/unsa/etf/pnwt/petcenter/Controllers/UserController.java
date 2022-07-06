package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/users")
    public @ResponseBody Iterable<User> getAllReservations(){
        return userRepository.findAll();
    }

    @PostMapping("/user")
    User newUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
