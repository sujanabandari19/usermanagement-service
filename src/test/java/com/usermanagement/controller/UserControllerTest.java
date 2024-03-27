package com.usermanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.User;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;
import com.usermanagement.sqs.SQSPublisher;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SQSPublisher sqsPublisher;
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(new UserDTO()));

        userController.getAllUsers();

    }

    @Test
    public void testGetUserById() {
        UserDTO userDto = new UserDTO();
        when(userService.getUserById(1L)).thenReturn(userDto);

        ResponseEntity<UserDTO> responseEntity = userController.getUserById(1L);

        assertEquals(userDto, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testCreateUser() {
        UserDTO userDto = new UserDTO();

        when(userService.createUser(userDto)).thenReturn(userDto);

        ResponseEntity<UserDTO> responseEntity = userController.createUser(userDto);

        assertEquals(userDto, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDetails = new UserDTO();

        when(userService.updateUser(1L, userDetails)).thenReturn(userDetails);
        
        ResponseEntity<UserDTO> responseEntity = userController.updateUser(1L, userDetails);
        verify(sqsPublisher, times(1)).publishMessage("updated user request");

        assertEquals(userDetails, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testSearchUsers() {
        when(userService.searchUsers("searchTerm")).thenReturn(Collections.singletonList(new UserDTO()));

        userController.searchUsers("searchTerm");

    }
}

