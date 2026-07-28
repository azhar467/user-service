package com.azhar.userservice.user.dto;

import jakarta.validation.constraints.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Schema(description = "Request payload for partially updating a user")
@Builder
public record UserPatchRequest(

        @Schema(
                description = "User's full name",
                example = "Azhar Ahmed"
        )
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @Schema(
                description = "User's email address",
                example = "azhar.ahmed467@gmail.com"
        )
        @Email(message = "Invalid email format")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email

) {}