package com.example.konko.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class EmailValidatorTest {
    @InjectMocks
    EmailValidator emailValidator;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void isValidEmailAddress() {
        //given
        String email = "owusu@gmail.com";
        //when
        boolean isValidEmail = emailValidator.isValidEmailAddress(email);
        assertThat(isValidEmail).isTrue();
    }


}