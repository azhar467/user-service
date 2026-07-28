package com.azhar.userservice.user.service;

import com.azhar.userservice.user.dto.UserCreateRequest;
import com.azhar.userservice.user.dto.UserPatchRequest;
import com.azhar.userservice.user.dto.UserResponse;
import com.azhar.userservice.user.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    UserResponse getUser(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserUpdateRequest request);

    UserResponse patchUser(Long id, UserPatchRequest request);

    void deleteUser(Long id);

}
