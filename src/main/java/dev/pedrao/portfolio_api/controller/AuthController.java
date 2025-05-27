package dev.pedrao.portfolio_api.controller;

import dev.pedrao.portfolio_api.model.User;
import dev.pedrao.portfolio_api.model.dto.AuthDTO;
import dev.pedrao.portfolio_api.model.dto.UserDTO;
import dev.pedrao.portfolio_api.service.UserService;
import dev.pedrao.portfolio_api.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordUtil passwordUtil;

    @Autowired
    public AuthController(UserService userService, PasswordUtil passwordUtil) {
        this.userService = userService;
        this.passwordUtil = passwordUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody AuthDTO credentials) {
        String username = credentials.username();
        String password = credentials.password();

        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty() || !passwordUtil.matches(password, userOptional.get().getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userOptional.get();

//        if(user.getPassword().isEmpty())
//            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).build();
        
        return ResponseEntity.ok(new UserDTO(user.getId(), user.getUsername(), user.getRole()));
    }
}