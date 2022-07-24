package com.example.konko.Utils;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator {

    public static final String VALID_EMAIL_REGEX = "^([a-zA-Z0-9_\\.\\-+])+@[a-zA-Z0-9-.]+\\.[a-zA-Z0-9-]{2,}$";

    public boolean isValidEmailAddress(String emailAddress) {
        return emailAddress.isEmpty() || emailAddress.matches(VALID_EMAIL_REGEX);
    }

}
