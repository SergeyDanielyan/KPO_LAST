package com.example.mikroservicebuy.controllers;

import com.example.mikroservicebuy.dto.OrderDto;
import com.example.mikroservicebuy.models.Order;
import com.example.mikroservicebuy.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "buy_methods")
@RestController
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @Operation(summary = "Пробует создать заказ")
    @PostMapping("/api/create")
    public ResponseEntity<String> createTicket(@RequestBody OrderDto orderDto) {
        try {
            Order order = ticketService.createTicket(orderDto);
            if (order == null) {
                throw new Exception("Couldn't create order");
            }
            return ResponseEntity.ok("Order is created");
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Возвращает информацию по заказу")
    @GetMapping("api/get-info")
    public ResponseEntity<String> getOrderInfoById(@RequestParam Integer id) {
        try {
            String result = ticketService.getOrderInfoById(id);
            return ResponseEntity.ok("Order: " + result);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
