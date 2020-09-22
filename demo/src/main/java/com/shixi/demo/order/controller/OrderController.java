package com.shixi.demo.order.controller;

import com.shixi.demo.order.entity.Order;
import com.shixi.demo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/getOrder")
    public List<Order> getOrder(String username){
        return orderService.getOrder(username);
    }

    @RequestMapping("/selectOrder")
    @ResponseBody
    public List<Order> selectOrder(@RequestBody Order order){
        return orderService.selectOrder(order);
    }

    @RequestMapping("/updateGps")
    @ResponseBody
    public void updateGps(@RequestBody Order order) {
        orderService.updateGps(order);
    }
}
