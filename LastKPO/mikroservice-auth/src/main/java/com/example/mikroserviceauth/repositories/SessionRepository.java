package com.example.mikroserviceauth.repositories;

import com.example.mikroserviceauth.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Session findByToken(String token);
}
