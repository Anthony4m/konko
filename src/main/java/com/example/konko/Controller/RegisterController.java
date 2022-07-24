package com.example.konko.Controller;

import com.example.konko.User.Model.UserModel;
import com.example.konko.User.User;
import com.example.konko.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app/user")
public class RegisterController {

    @Autowired
    private UserService userService;


    @PostMapping("/user/register")
    public String register(@RequestBody UserModel userModel){
       return userService.register(userModel);
    }

    @GetMapping(path = "confirmToken")
    public String confirmToken(@RequestParam("token") String confirmationToken) throws Exception {
        return userService.confirmToken(confirmationToken);
    }
    @GetMapping("/")
    public String home(){
        return "Here";
    }
}
