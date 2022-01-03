package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.Cart;
import com.geekbrains.spring.web.entities.Order;
import com.geekbrains.spring.web.entities.OrderItem;
import com.geekbrains.spring.web.entities.User;
import com.geekbrains.spring.web.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.repositories.OrderItemsRepository;
import com.geekbrains.spring.web.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductsService productsService;

    @Transactional
    public void createOrder(Cart cart, User user) {
        Order order = new Order(cart.getTotalPrice(), user);
        ordersRepository.save(order);
        cart.getItems().stream()
                .map(item -> new OrderItem(
                        item.getQuantity(),
                        item.getPricePerProduct(),
                        item.getPrice(),
                        user,
                        productsService.findById(item.getProductId())
                                .orElseThrow(() -> new ResourceNotFoundException("Product not found")),
                        order))
                .forEach(orderItem -> orderItemsRepository.save(orderItem));
    }
}
