package com.LMS.LibraryManagementSystem.controller;

import com.LMS.LibraryManagementSystem.DTO.ValidityResponse;
import com.LMS.LibraryManagementSystem.security.JwtService;
import com.LMS.LibraryManagementSystem.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/showLogin")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/userLogin")
    public String userLogin(@RequestParam String userName, @RequestParam String password, Model model,
                            HttpServletResponse response){
           ValidityResponse isValid = userService.validateUser(userName,password);
           if (isValid.isValid()) {
               JwtService jwtService = new JwtService();
               String jwtToken = jwtService.generateToken(isValid.getUsers());

               Cookie cookie = new Cookie("authToken",jwtToken);
               cookie.setHttpOnly(true);
               cookie.setPath("/");
               cookie.setMaxAge(3600);
               response.addCookie(cookie);

               model.addAttribute("userName", userName);
               return "home";
           }else{
               model.addAttribute("error","Invalid user name or password");
               return "login";
           }
    }
}
