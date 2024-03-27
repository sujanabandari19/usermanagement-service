package com.usermanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.usermanagement.common.RecordNotFoundException;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.User;
import com.usermanagement.mapper.UserMapper;
import com.usermanagement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetAllUsers() {
    	  User user = new User();
    	    user.setId(1L);

    	    when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

    	    List<UserDTO> result = userService.getAllUsers();
    	    assertEquals(1, result.size());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO userDto = new UserDTO();
        userDto.setId(1L);

        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDTO result = userService.getUserById(1L);

        assertEquals(userDto, result);
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    public void testCreateUser() {
        UserDTO userDto = new UserDTO();

        User user = new User();

        when(userMapper.toEntity(userDto)).thenReturn(user);

        when(userRepository.save(user)).thenReturn(user);

        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDTO result = userService.createUser(userDto);

        assertEquals(userDto, result);
    }

}

