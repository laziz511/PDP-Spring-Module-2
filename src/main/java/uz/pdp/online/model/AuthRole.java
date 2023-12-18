package uz.pdp.online.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthRole {
    private Long id;
    private String name;
    private List<AuthPermission> permissions;
}
