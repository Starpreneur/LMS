package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/showLogin")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/userLogin")
    public String userLogin(@RequestParam String userName, @RequestParam String password, Model model){
           boolean isValid = userService.validateUser(userName,password);
           if (isValid) {
               model.addAttribute("userName", userName);
               return "home";
           }else{
               model.addAttribute("error","Invalid user name or password");
               return "login";
           }
    }
}
