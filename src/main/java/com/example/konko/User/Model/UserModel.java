package com.example.konko.User.Model;

import com.example.konko.User.UserRole;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class UserModel {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
