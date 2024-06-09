package com.example.mikroserviceauth.repositories;

import com.example.mikroserviceauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.security.core.userdetails.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByNickname(String nickname);
}
