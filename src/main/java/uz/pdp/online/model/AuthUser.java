package uz.pdp.online.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    private Long id;
    private String username;
    private String password;
    private String role;
    private List<Subscription> subscriptions;
}
