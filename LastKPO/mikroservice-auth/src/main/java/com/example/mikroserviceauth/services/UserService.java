package com.example.mikroserviceauth.services;

import com.example.mikroserviceauth.DTO.UserDto;
import com.example.mikroserviceauth.models.Session;
import com.example.mikroserviceauth.models.User;
import com.example.mikroserviceauth.repositories.SessionRepository;
import com.example.mikroserviceauth.repositories.UserRepository;
import com.example.mikroserviceauth.validators.UserDtoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    enum MyRole implements GrantedAuthority {
        USER;

        @Override
        public String getAuthority() {
            return name();
        }
    }

    public boolean createUser(UserDto userDto) {
        if (!UserDtoValidator.isUserDtoValid(userDto)) {
            log.info("Email, nickname, password of user = {} are not valid", userDto);
            return false;
        }
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            log.info("User with email = {} already exists", userDto.getEmail());
            return false;
        }
        if (userRepository.findByNickname(userDto.getNickname()) != null) {
            log.info("User with nickname = {} already exists", userDto.getNickname());
            return false;
        }
        userDto.setCreated(LocalDateTime.now());
        log.info("Saved new user = {}", userRepository.save(User.builder()
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .password(userDto.getPassword())
                .created(userDto.getCreated())
                .build()));
        return true;
    }

    public String authUser(String email, String password) throws Exception {
        User myUser = userRepository.findByEmail(email);
        if (myUser == null) {
            log.info("There is no user with email = {}", email);
            throw new Exception("There is no such user");
        }
        if (!myUser.getPassword().equals(password)) {
            log.info("The password = {} is wrong", password);
            throw new Exception("There is no such user");
        }
        String myToken = jwtService.generateToken(myUser);
        Session session = Session.builder()
                .user(myUser)
                .token(myToken)
                .expired(LocalDateTime.now().plusHours(1))
                .build();
        sessionRepository.save(session);
        return myToken;
    }

    public UserDetails loadUserByEmail(String email) {
        if (email == null) {
            throw new UsernameNotFoundException("Wrong email");
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("There is no user with such email");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(MyRole.USER)
        );
    }

    public User getUserByToken(String token) throws Exception {
        Session session = sessionRepository.findByToken(token);
        if (session == null) {
            throw new NotFoundException("Wrong token");
        }
        return session.getUser();
    }
}
