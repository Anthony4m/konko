package com.example.konko.controller;
import com.example.konko.User.Dto.UserDto;
import com.example.konko.User.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api/app/")
public class RegisterController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String register(@RequestBody UserDto userModel){
       return userService.register(userModel);
    }

    @GetMapping(value = "user/{token}")
    public String confirmToken(@PathVariable("token") String confirmationToken) throws Exception {
        return userService.confirmToken(confirmationToken);
    }
    @GetMapping(value = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.generateRefreshToken(request,response);
    }
    @GetMapping("/")
    public String home(){
        return "Here";
    }
}
