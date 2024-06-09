package com.example.mikroservicebuy.repositories;

import com.example.mikroservicebuy.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByStatus(Integer status);
}
