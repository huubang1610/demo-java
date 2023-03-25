package com.config.swagger.controller;

import com.config.swagger.dto.UserRequest;
import com.config.swagger.dto.redisHash.UserRedis;
import com.config.swagger.entities.User;
import com.config.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }
    @GetMapping(value = "/{id}")
    @Operation(summary = "Search user by id")
    public ResponseEntity<User> getById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @GetMapping(value = "")
    @Operation(summary = "Find All")
    public ResponseEntity<List<UserRedis>> findAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(value = "/findby")
    @Operation(summary = "Find by Name")
    public ResponseEntity<List<UserRedis>> findByName(@RequestParam String name){
        return ResponseEntity.ok(userService.findByName(name));
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update user")
    public ResponseEntity<User> updateUser(@PathVariable(name = "id") Long id ,@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.update(id,userRequest));
    }
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete user by id")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id){
        userService.delete(id);
        return ResponseEntity.ok("Success");
    }
}
