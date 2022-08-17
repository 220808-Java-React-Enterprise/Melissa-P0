package com.revature.strong.ui;

import com.revature.strong.models.User;

public class MainMenu implements IMenu {
    private final User user;

    public MainMenu(User user) {
        this.user = user;
    }

    @Override
    public void start() {
        System.out.println("Welcome to Your STRONGest Self, " + user.getUsername() + "!");

    }
}
