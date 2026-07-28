package com.azhar.userservice;

import com.azhar.userservice.user.dto.UserResponse;
import com.azhar.userservice.user.entity.User;
import com.azhar.userservice.user.mapper.UserMapper;
import com.azhar.userservice.user.repository.UserRepository;
import com.azhar.userservice.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    private User user;
    private UserResponse response;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Azhar")
                .email("azhar.ahmed467@gmail.com")
                .build();

        response = UserResponse.builder()
                .id(1L)
                .name("Azhar")
                .email("azhar.ahmed467@gmail.com")
                .build();
    }

    @Test
    void shouldReturnUserWhenUserExists() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userMapper.toResponse(user))
                .thenReturn(response);
        UserResponse actual = userService.getUserById(1L);
        assertEquals(response,actual);
    }

}
