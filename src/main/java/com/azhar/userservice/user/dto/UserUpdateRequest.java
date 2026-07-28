package com.azhar.userservice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateRequest(@NotBlank(message = "Name is required") @Size(max = 100, message = "Name cannot exceed 100 characters") String name,
                                @NotBlank(message = "Email is required") @Email(message = "Invalid Format") String email) {
}
