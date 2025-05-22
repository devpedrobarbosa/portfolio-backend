package dev.pedrao.portfolio_api.config;

import dev.pedrao.portfolio_api.model.User;
import dev.pedrao.portfolio_api.repository.UserRepository;
import dev.pedrao.portfolio_api.util.PasswordUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class InitialUserSetup {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public InitialUserSetup(UserRepository userRepository, PasswordUtil passwordUtil) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
    }

    @PostConstruct
    public void createInitialUser() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(
                    "admin",
                    passwordUtil.encodePassword("0420"),
                    "ADMIN"
            );
            userRepository.save(admin);
        }
    }
}