package com.example.order_service.service;

import org.springframework.stereotype.Service;

import com.example.order_service.client.UserClient;
import com.example.order_service.dto.UserDTO;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;

    public OrderService(OrderRepository orderRepository, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order) {
        // Optional: fetch user info to check user exists or enrich order
        UserDTO user = userClient.getUserById(order.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found with id: " + order.getUserId());
        }
        // Save order if user exists
        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
