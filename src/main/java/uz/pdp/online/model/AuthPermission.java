package uz.pdp.online.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthPermission {
    private Long id;
    private String name;
}
