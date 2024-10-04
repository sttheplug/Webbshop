package com.example.webbshop.bo;

import com.example.webbshop.bo.Service.UserService;
import com.example.webbshop.bo.model.Product;
import com.example.webbshop.bo.model.User;

import java.util.ArrayList;

public class insertionuser {
    public static void main(String[] args) {
        UserService userService = new UserService();

        userService.registerUser("Alan", "12345", User.Role.customer);
    }
}
