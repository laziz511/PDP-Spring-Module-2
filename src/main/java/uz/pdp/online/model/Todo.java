package uz.pdp.online.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Todo {
    public Integer id;

    @NotBlank(message = "title.notBlank")
    public String title;

    @NotBlank(message = "priority.notBlank")
    @Size(max = 10, message = "priority.size")
    public String priority;

    public LocalDateTime createdAt;
    public Long userId;
}
