package com.example.konko.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    @Autowired
    private TokenServiceRepository tokenServiceRepository;


    public void saveToken(Token token) {
        tokenServiceRepository.save(token);
    }

    public Optional<Token> getToken(String confirmationToken) {
       return tokenServiceRepository.findByToken(confirmationToken);
    }

    public int setConfirmAt(String token) {
       return tokenServiceRepository.updateConfirmedAt(
                token, LocalDateTime.now()
        );

    }
}
