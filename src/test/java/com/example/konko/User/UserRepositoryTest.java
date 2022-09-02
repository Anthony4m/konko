package com.example.konko.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    String email = "owusu@gmail.com";
    User user = new User("nanaowusu","nana","owusu",email,"jahbless1",UserRole.ADMIN);


    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindIfEmailExists() {
        //given
        String email = "owusu@gmail.com";
        User user = new User("nanaowusu","nana","owusu",email,"jahbless1",UserRole.ADMIN);
        userRepository.save(user);
        //when
        boolean exists  = userRepository.findByEmail(user.getEmail()).isPresent();
        //then
        assertThat(exists).isTrue();
    }
    @Test
    void itShouldFindByEmail() {
        //given
        userRepository.save(user);
        //when
        User exists  = userRepository.findByEmail(user.getEmail()).get();
        //then
        assertThat(exists).isEqualTo(user);
    }
    @Test
    void itShouldFindByUserId(){
        //given
        userRepository.save(user);
        //when
        User userid = userRepository.findUserById(user.getId()).get();
        //then
        assertThat(userid).isEqualTo(user);
    }
}