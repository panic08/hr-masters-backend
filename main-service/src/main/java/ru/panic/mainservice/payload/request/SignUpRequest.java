package ru.panic.mainservice.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(@NotBlank(message = "Email must be correct") @Email(message = "Email must be correct") String email,
                            @NotBlank(message = "The password size can be from 4 to 24 characters long")
                            @Size(min = 4, max = 24, message = "The password size can be from 4 to 24 characters long") String password) {
}
