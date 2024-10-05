package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.dao.OrderDAO;

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

    public Order addOrder(User user, Timestamp orderDate){
        List<Product> items = user.getCart();
        if (items.size() == 0) {
            throw new RuntimeException();
        } else {
        int totalPrice = items.stream().mapToInt(Product::getPrice).sum();
        Order newOrder = new Order(user.getUserID(), totalPrice, orderDate, items);
        Order addedOrder = orderDAO.addOrder(newOrder);

        if (addedOrder != null) {
            user.clearCart();
        }

        return addedOrder;
        }
    }
    public boolean updateOrder(Order order){
        return this.updateOrder(order);
    }

    public static void main(String[] args){
        List<Order> orders;
        OrderService orderService = new OrderService();
        orders = orderService.getOrders();
        for(Order order:orders){
            System.out.println(order.getOrderId());
            System.out.println(order.getUserId());
            System.out.println(order.getTotalPrice());
            System.out.println(order.getOrderDate());
        }
    }
}
