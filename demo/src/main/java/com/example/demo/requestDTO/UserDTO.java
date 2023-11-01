package com.example.demo.requestDTO;

import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    @NotEmpty(message = "FirstName should not be empty")
    private String firstName;
    @NotEmpty(message = "LastName should not be empty")
    private String lastName;
    @NotEmpty(message = "Email should not be empty")
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
