package dev.pedrao.portfolio_api.model;

import dev.pedrao.portfolio_api.model.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean invalid() {
        return username.isEmpty() || password.isEmpty() || role.isEmpty();
    }

    public UserDTO toDTO() {
        return new UserDTO(id, username, role);
    }
}