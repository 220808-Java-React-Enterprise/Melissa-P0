package com.revature.strong.ui;

import com.revature.strong.models.User;
import com.revature.strong.services.UserService;

public class MainMenu implements IMenu {
    private final User user;
    private final UserService userService;

    public MainMenu(User user, UserService userService) {
        this.user = user;
        this.userService = userService;
    }

    @Override
    public void start() {
        System.out.println("Welcome to Your STRONGest Self, " + user.getUsername() + "!");
        System.out.println("Please select from the menu below: \n[1]Shop\n[2]View messages from Coach\n[3]View workout plan\n[X]Exit");

    }
}
