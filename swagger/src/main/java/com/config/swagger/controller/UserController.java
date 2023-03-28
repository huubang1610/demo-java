package com.config.swagger.controller;

import com.config.swagger.dto.LoginRequest;
import com.config.swagger.dto.UserDetails;
import com.config.swagger.dto.UserRequest;
import com.config.swagger.dto.redisHash.UserRedis;
import com.config.swagger.entities.User;
import com.config.swagger.service.UserService;
import com.config.swagger.utils.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<UserDetails> login(@RequestBody LoginRequest loginUser) {
        return ResponseEntity.ok(userService.login(loginUser));
    }

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Search user by id")
    public ResponseEntity<User> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping(value = "")
    @Operation(summary = "Find All")
    public ResponseEntity<ListResult<UserRedis>> findAll(@RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                         @RequestParam(name = "size", defaultValue = "5", required = false) int size,
                                                         @RequestParam(name = "orderBy", defaultValue = "CREATED_AT", required = false) String orderBy,
                                                         @RequestParam(name = "ascending", required = false) boolean desc) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, orderBy, desc));
    }

    @GetMapping(value = "/findby")
    @Operation(summary = "Find by Name")
    public ResponseEntity<ListResult<UserRedis>> findByName(@RequestParam(name = "name", required = false) String name,
                                                            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
                                                            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
                                                            @RequestParam(name = "orderBy", defaultValue = "CREATED_AT", required = false) String orderBy,
                                                            @RequestParam(name = "ascending", required = false) boolean desc) {
        return ResponseEntity.ok(userService.findByName(name, page, size, orderBy, desc));
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") Long id, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.update(id, userRequest));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete user by id")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok("Success");
    }
}
