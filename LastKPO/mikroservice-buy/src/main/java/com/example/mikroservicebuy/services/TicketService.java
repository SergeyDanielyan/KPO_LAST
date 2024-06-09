package com.example.mikroservicebuy.services;

import com.example.mikroservicebuy.dto.OrderDto;
import com.example.mikroservicebuy.models.Order;
import com.example.mikroservicebuy.models.Station;
import com.example.mikroservicebuy.repositories.OrderRepository;
import com.example.mikroservicebuy.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {
    private final OrderRepository orderRepository;
    private final StationRepository stationRepository;

    private String getStatusNameByNumber(int number) {
        switch (number) {
            case 1:
                return "check";
            case 2:
                return "success";
            case 3:
                return "rejection";
            default:
                return "unknown";
        }
    }

    private String orderToString(Order order) {
        return String.format("id: %d, userId: %d, from station %s to station %s at %s is %s",
                order.getId(), order.getUserId(), order.getFromStation().getName(), order.getToStation().getName(),
                order.getCreated().toString(), getStatusNameByNumber(order.getStatus()));
    }

    private Integer getUserIdByToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8085/api/user/user-info?token=" + token;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String strResponse = response.toString();
            int idFirstIndex = strResponse.indexOf("id") + 3;
            int idLastIndex = idFirstIndex;
            while (strResponse.charAt(idLastIndex) >= '0' && strResponse.charAt(idLastIndex) <= '9') {
                ++idLastIndex;
            }
            return Integer.parseInt(strResponse.substring(idFirstIndex, idLastIndex));
        }
        return -1;
    }

    public Order createTicket(OrderDto orderDto) {
        Station fromStation = stationRepository.findByName(orderDto.getFromStationName());
        Station toStation = stationRepository.findByName(orderDto.getToStationName());
        if (fromStation == null || toStation == null) {
            log.info("There are no stations with names {} and {}", orderDto.getFromStationName(),
                    orderDto.getToStationName());
            return null;
        }
        Integer userId = getUserIdByToken(orderDto.getToken());
        if (userId == -1) {
            log.info("There is no authorized user with token {}", orderDto.getToken());
            return null;
        }
        Order order = orderRepository.save(Order.builder()
                .userId(userId).fromStation(fromStation).toStation(toStation)
                .created(LocalDateTime.now()).status(1).build());
        log.info("Ticket {} is created", order);
        return order;
    }

    public String getOrderInfoById(Integer id) throws Exception {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new Exception("There is no order with such id");
        }
        return orderToString(order);
    }

    Integer statusGenerator() {
        Random random = new Random();
        int x = random.nextInt(100);
        if (x >= 20) {
            return 2;
        } else {
            return 3;
        }
    }

    @Scheduled(fixedRate = 10000)
    public void statusUpdater() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<Order> orders = orderRepository.findAllByStatus(1);
        for (Order order : orders) {
            order.setStatus(statusGenerator());
            orderRepository.deleteById(order.getId());
            orderRepository.save(order);
        }
    }
}