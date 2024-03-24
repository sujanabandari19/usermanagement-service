package com.usermanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.usermanagement.dto.UserDTO;


public interface UserService {
      
    
    public List<UserDTO> getAllUsers();

    public UserDTO getUserById(Long userId);
    
    public UserDTO createUser(UserDTO userDTO);

    public UserDTO updateUser(Long userId, UserDTO userDto);

    public ResponseEntity<?> deleteUser(Long userId);

	List<UserDTO> searchUsers(String searchTerm);
    
    
}

