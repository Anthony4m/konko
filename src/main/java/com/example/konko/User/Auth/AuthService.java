package com.example.konko.User.Auth;

import com.example.konko.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    public Authentication authenticate(Token token) {
        return   authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        token.getUser().getUsername(),
                        token.getUser().getPassword()
                ));
    }
}
