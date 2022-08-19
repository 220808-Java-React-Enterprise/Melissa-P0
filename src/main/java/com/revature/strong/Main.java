package com.revature.strong;

import com.revature.strong.daos.UserDAO;
import com.revature.strong.services.UserService;
import com.revature.strong.ui.LoginMenu;
import com.revature.strong.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] arg) throws IOException, SQLException {
       //dependency injection
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        new LoginMenu(userService).start();

        try {
            System.out.println(ConnectionFactory.getInstance().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
