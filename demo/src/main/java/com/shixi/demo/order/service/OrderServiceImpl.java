package com.shixi.demo.order.service;

import com.shixi.demo.order.dao.OrderDao;
import com.shixi.demo.order.entity.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDao orderDao;
    @Override
    public List<Order> getOrder(String username){
        return orderDao.getOrder(username);
    }
    @Override
    public List<Order> selectOrder(Order order){
        return orderDao.selectOrder(order);
    }
    @Override
    public void updateGps(Order order) {
        orderDao.updateGps(order);
    }
}
