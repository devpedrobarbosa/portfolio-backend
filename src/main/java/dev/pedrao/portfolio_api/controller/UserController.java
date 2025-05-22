package dev.pedrao.portfolio_api.controller;

import dev.pedrao.portfolio_api.model.User;
import dev.pedrao.portfolio_api.service.UserService;
import dev.pedrao.portfolio_api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordUtil passwordUtil;

    @Autowired
    public UserController(UserService userService, PasswordUtil passwordUtil) {
        this.userService = userService;
        this.passwordUtil = passwordUtil;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.create(user);
        user.setPassword(passwordUtil.encodePassword(user.getPassword()));
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        user.setPassword(passwordUtil.encodePassword(user.getPassword()));
        User updatedUser = userService.update(id, user);
        if(updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}