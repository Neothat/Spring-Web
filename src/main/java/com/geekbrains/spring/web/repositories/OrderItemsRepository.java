package com.geekbrains.spring.web.repositories;

import com.geekbrains.spring.web.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
}
