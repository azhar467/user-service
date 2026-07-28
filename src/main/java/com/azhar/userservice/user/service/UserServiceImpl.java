package com.azhar.userservice.user.service;

import com.azhar.userservice.exception.UserAlreadyExistsException;
import com.azhar.userservice.exception.UserNotFoundException;
import com.azhar.userservice.user.dto.UserCreateRequest;
import com.azhar.userservice.user.dto.UserPatchRequest;
import com.azhar.userservice.user.dto.UserResponse;
import com.azhar.userservice.user.dto.UserUpdateRequest;
import com.azhar.userservice.user.entity.User;
import com.azhar.userservice.user.mapper.UserMapper;
import com.azhar.userservice.user.model.Role;
import com.azhar.userservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new UserAlreadyExistsException("Email already exists");
        }
        User user = userMapper.toEntity(request);
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: "+id));
        if (!user.getEmail().equals(request.email())
                && userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        user.setName(request.name());
        user.setEmail(request.email());
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse patchUser(Long id, UserPatchRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: "+id));

        if (request.userName()!= null){
            if (request.userName().isBlank()){
                throw new IllegalArgumentException("Name cannot be blank");
            }
            user.setName(request.userName());
        }

        if (request.email()!=null){
            if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())){
                throw new UserAlreadyExistsException("User Already Exists");
            }
            user.setEmail(request.email());
        }
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found with id: "+id));
        userRepository.delete(user);
    }

}
