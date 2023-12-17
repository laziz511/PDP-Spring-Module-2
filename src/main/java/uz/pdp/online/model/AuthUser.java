package uz.pdp.online.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    private Long id;
    private String username;
    private String password;
}
