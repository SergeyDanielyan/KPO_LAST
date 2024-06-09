package com.example.mikroservicebuy.repositories;

import com.example.mikroservicebuy.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Integer> {
    Station findByName(String name);
}
