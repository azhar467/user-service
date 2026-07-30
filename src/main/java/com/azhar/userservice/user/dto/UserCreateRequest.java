package com.azhar.userservice.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Schema(description = "Request payload for creating a user")
@Builder
public record UserCreateRequest(

        @Schema(example = "Azhar Ahmed")
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @Schema(example = "azhar.ahmed467@gmail.com")
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid Format")
        String email
) {}
