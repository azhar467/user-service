package com.azhar.userservice;

import com.azhar.userservice.exception.UserAlreadyExistsException;
import com.azhar.userservice.exception.UserNotFoundException;
import com.azhar.userservice.user.dto.UserCreateRequest;
import com.azhar.userservice.user.dto.UserPatchRequest;
import com.azhar.userservice.user.dto.UserResponse;
import com.azhar.userservice.user.dto.UserUpdateRequest;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    private User user;
    private User user2;
    private User user3;
    private List<User> userList;
    private UserResponse response;
    private UserResponse response2;
    private UserResponse response3;
    private List<UserResponse> responseList;

    private UserCreateRequest request;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Azhar")
                .email("azhar.ahmed467@gmail.com")
                .build();

        user2 = User.builder()
                .id(2L)
                .name("John Doe")
                .email("john.doe@yahoo.com")
                .build();

        user3 = User.builder()
                .id(3L)
                .name("Margaret Kroon")
                .email("margaret.kroon@hotmail.com")
                .build();

        userList = List.of(user, user2, user3);

        request = UserCreateRequest.builder()
                .name("Azhar").email("azhar.ahmed467@gmail.com").build();

        response = UserResponse.builder()
                .id(1L)
                .name("Azhar")
                .email("azhar.ahmed467@gmail.com")
                .build();

        response2 = UserResponse.builder()
                .id(2L)
                .name("John Doe")
                .email("john.doe@yahoo.com")
                .build();
        response3 = UserResponse.builder()
                .id(3L)
                .name("Margaret Kroon")
                .email("margaret.kroon@hotmail.com")
                .build();

        responseList = List.of(response, response2, response3);
    }

    @Test
    void shouldReturnUserWhenUserExists() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userMapper.toResponse(user))
                .thenReturn(response);
        UserResponse actual = userService.getUserById(1L);
        assertEquals(response, actual);
        verify(userRepository, times(1)).findById(1L);

    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(1L)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userMapper, never()).toResponse(any());
    }

    @Test
    void shouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toResponse(user)).thenReturn(response);
        when(userMapper.toResponse(user2)).thenReturn(response2);
        when(userMapper.toResponse(user3)).thenReturn(response3);
        List<UserResponse> actualResponses = userService.getAllUsers();
        assertEquals(responseList, actualResponses);
    }

    @Test
    void shouldReturnEmptyListWhenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(List.of());
        List<UserResponse> actual = userService.getAllUsers();
        assertEquals(List.of(), actual);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);
        UserResponse actual = userService.createUser(request);
        assertEquals(response, actual);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(request.email())).thenReturn(true);
        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.createUser(request));
        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .name("Azhar A").email(request.email()).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse updatedResponse = UserResponse.builder()
                .name(updateRequest.name()).email(updateRequest.email()).build();
        when(userMapper.toResponse(user)).thenReturn(updatedResponse);
        UserResponse actual = userService.updateUser(1L, updateRequest);
        assertEquals(updatedResponse, actual);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUpdatingNonExistingUser() {
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .name("Azhar A").email(request.email()).build();
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                ()-> userService.updateUser(1L, updateRequest));
        assertEquals("User not found with id: 1",exception.getMessage());
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenUpdatingWithExistingEmail() {
        UserUpdateRequest updateRequest = UserUpdateRequest.builder()
                .name(request.name()).email(user2.getEmail()).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(updateRequest.email())).thenReturn(true);
        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                ()-> userService.updateUser(1L, updateRequest));
        assertEquals("Email already exists",exception.getMessage());
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenDeletingNonExistingUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class, ()-> userService.deleteUser(1L));
        assertEquals("User not found with id: 1",exception.getMessage());
    }

    @Test
    void shouldUpdateOnlyNameWhenOnlyNameIsProvided(){
        UserPatchRequest patchRequest = UserPatchRequest.builder().name("Azhar A").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse patchResponse = UserResponse.builder().name(patchRequest.name()).build();
        when(userMapper.toResponse(user)).thenReturn(patchResponse);
        UserResponse actual = userService.patchUser(1L,patchRequest);
        assertEquals(patchResponse,actual);
    }

    @Test
    void shouldUpdateOnlyEmailWhenOnlyEmailIsProvided() {
        UserPatchRequest patchRequest = UserPatchRequest.builder()
                .email("azhar.ahmed467@gmail.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse patchResponse = UserResponse.builder()
                .name(patchRequest.name()).build();
        when(userMapper.toResponse(user)).thenReturn(patchResponse);
        UserResponse actual = userService.patchUser(1L,patchRequest);
        assertEquals(patchResponse,actual);
    }

    @Test
    void shouldUpdateNameAndEmailWhenBothAreProvided() {
        UserPatchRequest patchRequest = UserPatchRequest.builder().name("Azhar A")
                .email("azhar.ahmed467@gmail.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse patchResponse = UserResponse.builder()
                .name(patchRequest.name()).build();
        when(userMapper.toResponse(user)).thenReturn(patchResponse);
        UserResponse actual = userService.patchUser(1L,patchRequest);
        assertEquals(patchResponse,actual);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNameIsBlank() {
        UserPatchRequest patchRequest = UserPatchRequest.builder().name("").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                ()->userService.patchUser(1L, patchRequest));
        assertEquals("Name cannot be blank",actual.getMessage());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenPatchingNonExistingUser() {
        UserPatchRequest patchRequest = UserPatchRequest.builder().build();
        UserNotFoundException actual = assertThrows(UserNotFoundException.class,
                ()->userService.patchUser(1L,patchRequest));
        assertEquals("User not found with id: 1",actual.getMessage());
    }

    @Test
    void shouldThrowUserAlreadyExistsExceptionWhenPatchingWithExistingEmail() {
        UserPatchRequest patchRequest = UserPatchRequest.builder()
                .email("azhar.ahmed@gmail.com").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(patchRequest.email())).thenReturn(true);
        UserAlreadyExistsException actual = assertThrows(UserAlreadyExistsException.class,
                ()-> userService.patchUser(1L,patchRequest));
        assertEquals("User Already Exists",actual.getMessage());

    }

}
