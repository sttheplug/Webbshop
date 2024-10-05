package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Order;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;
import com.example.webbshop.db.dao.OrderDAO;
import com.example.webbshop.ui.DTO.OrderDTO;
import com.example.webbshop.ui.DTO.ProductDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private final OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }
    public Order getOrderById(int orderId) {
        return this.orderDAO.getOrderById(orderId);
    }

    public Order addOrder(User user, Timestamp orderDate){
        List<Product> items = user.getCart();
        if (items.isEmpty()) {
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

    public List<Order> getAllOrders(){
        return this.orderDAO.getAllOrders();
    }
    public boolean updateOrder(Order order){
        return this.updateOrder(order);
    }

}
