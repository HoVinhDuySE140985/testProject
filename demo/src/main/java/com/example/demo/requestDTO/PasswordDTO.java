package com.example.demo.requestDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PasswordDTO {
    @NotEmpty(message = "password should not be empty")
    private String password;
    @NotEmpty(message = "confirmPassword should not be empty")
    private String confirmPassword;
}
