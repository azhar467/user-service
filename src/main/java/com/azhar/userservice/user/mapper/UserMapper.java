package com.azhar.userservice.user.mapper;

import com.azhar.userservice.user.dto.UserCreateRequest;
import com.azhar.userservice.user.dto.UserResponse;
import com.azhar.userservice.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserCreateRequest request){
        return User.builder().name(request.name()).email(request.email()).build();
    }

    public UserResponse toResponse(User user){
        return UserResponse.builder().id(user.getId()).name(user.getName())
                .email(user.getEmail()).role(user.getRole()).build();
    }
}