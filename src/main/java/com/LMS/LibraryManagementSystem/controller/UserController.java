package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.DTO.UsersDTO;
import com.LMS.LibraryManagementSystem.model.Users;
import com.LMS.LibraryManagementSystem.repository.UserRepository;
import com.LMS.LibraryManagementSystem.service.UserService;
import com.LMS.LibraryManagementSystem.utils.PasswordUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody UsersDTO usersDTO){
        Users savedUser = userService.createUser(usersDTO);
        if (savedUser != null){
            return new ResponseEntity<>("User created Successfully "+savedUser.getUserId(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("User was not created,Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
