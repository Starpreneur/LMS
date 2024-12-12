package com.LMS.LibraryManagementSystem.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/logOut")
public class LogOutController {

    @GetMapping("/userLogOut")
    public String userLogOut(HttpServletRequest request, HttpServletResponse response){

        Cookie cookie = new Cookie("authToken",null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return "redirect:/api/login/showLogin";

    }
}
