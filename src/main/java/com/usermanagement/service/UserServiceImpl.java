package com.usermanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.usermanagement.common.RecordNotFoundException;
import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.User;
import com.usermanagement.mapper.UserMapper;
import com.usermanagement.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
      
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDtos(users); 
    }
    
    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RecordNotFoundException("User", "id", userId));
        return userMapper.toUserDto(user);
    }
    
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User createdUser = userRepository.save(user);
        return userMapper.toUserDto(createdUser);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDto) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RecordNotFoundException("User", "id", userId));
        User updatedUser = userMapper.updateEntity(user, userDto);
        updatedUser = userRepository.save(updatedUser);
        return userMapper.toUserDto(updatedUser);
    }
    
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RecordNotFoundException("User", "id", userId));

        userRepository.delete(user);

       
    }
    
    @Override
    public List<UserDTO> searchUsers(String searchTerm) {
        List<User> users = userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(searchTerm, searchTerm, searchTerm);
        return userMapper.toUserDtos(users); 

    }
}

