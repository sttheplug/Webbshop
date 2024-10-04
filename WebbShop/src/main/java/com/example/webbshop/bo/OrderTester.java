package com.example.webbshop.bo;

import com.example.webbshop.bo.Service.OrderService;
import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderTester {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        // 1. Add an Order
        List<Product> items = new ArrayList<>(); // Assume an empty product list for now
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());

        Order newOrder = orderService.addOrder(4, 500, orderDate, items);
        System.out.println("Order added: " + newOrder.getOrderId());
    }
}
