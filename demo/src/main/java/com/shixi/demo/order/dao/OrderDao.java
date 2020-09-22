package com.shixi.demo.order.dao;


import com.shixi.demo.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    List<Order> getOrder(String name);
    List<Order> selectOrder(Order order);
    public void updateGps(Order order);
}
