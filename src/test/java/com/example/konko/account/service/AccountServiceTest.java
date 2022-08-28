package com.example.konko.account.service;

import com.example.konko.User.User;
import com.example.konko.User.UserRepository;
import com.example.konko.User.UserRole;
import com.example.konko.account.Account;
import com.example.konko.account.AccountType;
import com.example.konko.account.dto.AccountDto;
import com.example.konko.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    @InjectMocks
    AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    User user = new User("nanaowusu","nana","owusu","owusu@gmail.com","jahbless1", UserRole.ADMIN);
    @Mock
    AccountDto account = new AccountDto("nana owusu","12345678",true,12.00,AccountType.SAVING,1L);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void canCreateAccount() {
        //given
        when(userRepository.save(any(User.class))).thenReturn(user);
        //when
        String createUserAccount = accountService.createAccount(account);
        //then
        assertNotNull(createUserAccount);
//        assertThat(createUserAccount).isEqualTo(user.getUsername());
    }

    @Test
    @Disabled
    void getAccount() {
    }

    @Test
    @Disabled
    void testGetAccount() {
    }

    @Test
    @Disabled
    void deleteAccount() {
    }

    @Test
    @Disabled
    void updateAccountBalance() {
    }

    @Test
    @Disabled
    void updateAccount() {
    }
}