package com.example.webbshop.bo;

import com.example.webbshop.bo.Service.OrderService;
import com.example.webbshop.bo.Service.ProductService;
import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import java.sql.Date;
import java.sql.Timestamp;

public class OrderTester {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        UserService userService = new UserService();
        ProductService productService = new ProductService();

        Product product = productService.getProductById(9);
        User user = userService.searchUserById(6);

        user.addToCart(product);

        System.out.println("test adding order");
        Order createdOrder = orderService.addOrder(user, new Timestamp(System.currentTimeMillis()));
        System.out.println(createdOrder.getOrderId());
    }
}
