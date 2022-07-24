package com.example.konko.User.Service;

import com.example.konko.User.Auth.AuthService;
import com.example.konko.User.Model.UserModel;
import com.example.konko.User.User;
import com.example.konko.User.UserRepository;
import com.example.konko.User.UserRole;
import com.example.konko.Utils.EmailValidator;
import com.example.konko.Utils.JWTUtility;
import com.example.konko.Utils.PasswordEncoder;
import com.example.konko.token.Token;
import com.example.konko.token.TokenService;
import com.example.konko.token.TokenServiceRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->{
            new UsernameNotFoundException("User not found");
            return null;
        });
    }

    public String register(UserModel userModel){
    boolean isValidEmail = emailValidator.isValidEmailAddress(userModel.getEmail());

    if (!isValidEmail){
        throw new IllegalStateException("email Is invalid");
    }

    return signUpUser(
            new User(
                    userModel.getUserName(),
                    userModel.getFirstName(),
                    userModel.getLastName(),
                    userModel.getEmail(),
                    userModel.getPassword(),
                    UserRole.USER));
    }

    private String signUpUser(User user) {
//  Check if user already Exist
    boolean userExist = userRepository.findByEmail(user.getEmail()).isPresent();
    if (userExist){
        throw new IllegalStateException("User Already Exists");
    }
    //Hash password
    String passwordEncoded = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
    user.setPassword(passwordEncoded);
    userRepository.save(user);

    //Send Token
    String tokenGenerated = UUID.randomUUID().toString();
    Token token = new Token(
            tokenGenerated,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
    );
    //Save Token
        tokenService.saveToken(token);

    return tokenGenerated;
    }

    public String confirmToken(String confirmationToken) throws Exception {
        //check if token is valid
        Token token = tokenService.getToken(confirmationToken).orElseThrow(()->{
            throw new IllegalStateException("Token is invalid");
        });
//        try {
//            authService.authenticate(token);
//        }catch (BadCredentialsException e){
//            throw  new Exception("invalid Credentials");
//        }

        //check if token expired
        LocalDateTime expiredAt = token.getExpiredAt();
        if (Objects.equals(expiredAt, LocalDateTime.now())){
            throw new IllegalStateException("Token expired");
        }

        tokenService.setConfirmAt(confirmationToken);

        //enable User
        enableUser(token.getUser().getEmail());

        return jwtUtility.generateAuthToken(token.getUser());
    }

    private void enableUser(String email) {
        userRepository.enableUser(email);
    }
}
