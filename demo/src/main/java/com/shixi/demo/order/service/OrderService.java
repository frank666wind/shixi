package com.shixi.demo.order.service;

import com.shixi.demo.order.entity.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;

public interface OrderService {
    List<Order> getOrder(String username);
    List<Order> selectOrder(Order order);
    public void updateGps(Order order);
}
