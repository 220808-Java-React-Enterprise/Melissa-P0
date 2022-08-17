package com.revature.strong.ui;

import com.revature.strong.daos.UserDAO;
import com.revature.strong.services.UserService;

import java.io.IOException;

public class Main {
    public static void main(String[] arg) throws IOException {
        //dependency injection
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        new LoginMenu(userService).start();

    }
}
