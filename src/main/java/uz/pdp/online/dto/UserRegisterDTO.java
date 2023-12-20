package uz.pdp.online.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRegisterDTO {

    @NotBlank(message = "username_error_message_not_null")
    public String username;

    @NotBlank(message = "password_error_message_not_null")
    @Size(min = 8, message = "password_error_message_not_enough_length")
    public String password;
}
