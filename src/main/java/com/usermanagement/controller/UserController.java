package com.usermanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(description = "Crud operations on Users", name = "UserManagement APIs")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(method = "getAllUsers", description = "Returns all the available users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(method = "getUserById", description = "Return the requested user")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") Long userId) {
    	UserDTO userDto = userService.getUserById(userId);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping
    @Operation(method = "createUser", description = "Save User")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
        UserDTO createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(method = "updateUser", description = "Update the user")
    public ResponseEntity<UserDTO> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody UserDTO userDetails) {
    	UserDTO updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(method = "deleteUser", description = "Delete the user")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
        return userService.deleteUser(userId);
    }
    
    @GetMapping("/search")
    public List<UserDTO> searchUsers(@RequestParam String searchTerm) {
        return userService.searchUsers(searchTerm);
    }
}

