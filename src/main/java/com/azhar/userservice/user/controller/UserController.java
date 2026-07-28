package com.azhar.userservice.user.controller;

import com.azhar.userservice.user.dto.UserCreateRequest;
import com.azhar.userservice.user.dto.UserPatchRequest;
import com.azhar.userservice.user.dto.UserResponse;
import com.azhar.userservice.user.dto.UserUpdateRequest;
import com.azhar.userservice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserCreateRequest request){
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request){
        return userService.updateUser(id, request);
    }

    @PatchMapping("/{id}")
    public UserResponse patchUser(@PathVariable Long id, @Valid @RequestBody UserPatchRequest request){
        return userService.patchUser(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
