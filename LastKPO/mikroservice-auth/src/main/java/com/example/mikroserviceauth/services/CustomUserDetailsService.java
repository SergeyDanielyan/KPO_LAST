package com.example.mikroserviceauth.services;

import com.example.mikroserviceauth.models.User;
import com.example.mikroserviceauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService { // implements UserDetailsService {
    /*
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword());
        //return new User(user.getEmail(), user.getNickname(), user.getPassword(), user.getCreated());org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
     */
}