package rs.ac.bg.fon.airbnb_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.airbnb_backend.domain.User;
import rs.ac.bg.fon.airbnb_backend.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public void save(@RequestBody User user) {
        userRepository.save(user);
    }

    @DeleteMapping("{userId}")
    public void delete(@PathVariable Long userId) {
        userRepository.delete(userId);
    }

    @PatchMapping("{userId}")
    public void update(@PathVariable Long userId, @RequestBody User user) {
        userRepository.update(userId, user);
    }

    @PostMapping("/newGender/{newGender}")
    public void addNewGender(@PathVariable String newGender) {
        System.out.println(newGender);
        userRepository.addNewGender(newGender);
    }
}
