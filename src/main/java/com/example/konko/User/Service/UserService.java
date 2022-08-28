package com.example.konko.User.Service;

import com.example.konko.User.Auth.AuthService;
import com.example.konko.User.Dto.UserDto;
import com.example.konko.User.User;
import com.example.konko.User.UserRepository;
import com.example.konko.User.UserRole;
import com.example.konko.Utils.AuthFilter;
import com.example.konko.Utils.EmailValidator;
import com.example.konko.Utils.JWTUtility;
import com.example.konko.Utils.PasswordEncoder;
import com.example.konko.token.Token;
import com.example.konko.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthFilter authFilter;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        if(user == null) {
            new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    public String register(UserDto userModel){
    boolean isValidEmail = emailValidator.isValidEmailAddress(userModel.getEmail());

    if (!isValidEmail){
        return  "email Is invalid";
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
        return "User Already Exists";
    }
//    //Hash password
    String passwordEncoded = passwordEncoder.encode(user.getPassword());
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
    public void generateRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authoriztionHeader = request.getHeader(AUTHORIZATION);
        if(authoriztionHeader != null && authoriztionHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authoriztionHeader.substring("Bearer ".length());
                String subject = authFilter.vaildateAuthToken(request,authoriztionHeader);
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) loadUserByUsername(subject);
                authFilter.generateAuthToken(request,response,user,refresh_token);
            }catch (Exception e){
                log.error("Error Loggin in{}", e.getMessage());
                response.setHeader("Error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("Error_Message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }else {
            throw new  RuntimeException("Refresh token Missing");
        }
    }
}
