package com.azhar.userservice.user.dto;

import jakarta.validation.constraints.Email;

public record UserPatchRequest(String userName, @Email String email) {
}
