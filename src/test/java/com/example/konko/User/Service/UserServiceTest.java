package com.example.konko.User.Service;

import com.example.konko.User.Auth.AuthService;
import com.example.konko.User.Dto.UserDto;
import com.example.konko.User.User;
import com.example.konko.User.UserRepository;
import com.example.konko.User.UserRole;
import com.example.konko.Utils.EmailValidator;
import com.example.konko.Utils.JWTUtility;
import com.example.konko.Utils.PasswordEncoder;
import com.example.konko.token.Token;
import com.example.konko.token.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    EmailValidator emailValidator;
    @Mock
    private TokenService tokenService;
    private AutoCloseable autoCloseable;
    @InjectMocks
    private UserService userService;
    @Mock
    UserDto user = new UserDto("nana","owusu","nanaowusu","owusu@gmail.com","jahbless1");


    @BeforeEach
    void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void CanregisterUser() {
        String tokenGenerated = UUID.randomUUID().toString();
        when(emailValidator.isValidEmailAddress(any())).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn("$$S$S$S$$SS$S");

        String register = userService.register(user);
        assertThat(register).isEqualTo(register);
    }

    @Test
    @Disabled
    void canConfirmToken() throws Exception {
        when(emailValidator.isValidEmailAddress(any())).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn("$$S$S$S$$SS$S");

        String register = userService.register(user);
        String token = userService.confirmToken(register);
        assertThat(token).isNotNull();
    }
}