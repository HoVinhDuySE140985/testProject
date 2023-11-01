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
public class UserUpdateDTO {
    private Long id;
    @NotEmpty(message = "FirstName should not be empty")
    private String newFirstName;
    @NotEmpty(message = "LastName should not be empty")
    private String newLastName;
}
