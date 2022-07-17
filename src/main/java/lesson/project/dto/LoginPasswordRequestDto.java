package lesson.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginPasswordRequestDto {
    @NotBlank(message = "Login is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
