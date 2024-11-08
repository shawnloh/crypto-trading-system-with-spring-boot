package tech.betterwith.tradingsystem.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
