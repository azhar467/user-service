package com.azhar.userservice.user.dto;

import com.azhar.userservice.user.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "Response returned after retrieving or modifying a user")
@Builder
public record UserResponse(

        @Schema(description = "Unique identifier of the user", example = "1")
        Long id,

        @Schema(description = "User's full name", example = "Azhar Ahmed")
        String name,

        @Schema(description = "User's email address", example = "azhar.ahmed467@gmail.com")
        String email,

        @Schema(description = "Role assigned to the user", example = "USER")
        Role role,

        @Schema(description = "Whether the user account is active", example = "true")
        Boolean active,

        @Schema(description = "Timestamp when the user was created", example = "2026-07-28T14:30:15")
        LocalDateTime createdAt,

        @Schema(description = "Timestamp when the user was last updated", example = "2026-07-28T15:45:20")
        LocalDateTime updatedAt
) {}