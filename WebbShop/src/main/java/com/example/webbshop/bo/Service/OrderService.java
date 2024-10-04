package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.db.dao.OrderDAO;
import com.example.webbshop.db.dao.UserDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }
    public Order getOrderById(int orderId) {
        return this.orderDAO.getOrderById(orderId);
    }

    public List<Order> getOrders(){
        return this.orderDAO.getAllOrders();
    }

    public Order addOrder(int userId, int totalPrice, Timestamp orderDate, List<Product> items){

        return this.orderDAO.addOrder(new Order(userId, totalPrice, orderDate, items));
    }
    public static void main(String[] args) {
        // Create an instance of the OrderService


    }
}
