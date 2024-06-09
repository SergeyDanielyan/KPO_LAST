package com.example.mikroservicebuy.dto;

import lombok.Data;

@Data
public class OrderDto {
    String token;
    String fromStationName;
    String toStationName;
}
