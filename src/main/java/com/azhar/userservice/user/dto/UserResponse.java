package com.azhar.userservice.user.dto;

import com.azhar.userservice.user.model.Role;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String name, String email, Role role) {
}
