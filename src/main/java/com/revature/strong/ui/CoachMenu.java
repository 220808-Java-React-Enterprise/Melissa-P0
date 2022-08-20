package com.revature.strong.ui;

import com.revature.strong.models.User;
import com.revature.strong.services.UserService;

public class CoachMenu implements IMenu {
    private final User user;
    private final UserService userService;

    public CoachMenu(User user, UserService userService) {
        this.user = user;
        this.userService = userService;
    }

    @Override
    public void start() {
        System.out.println("\nWelcome to Your STRONGest self, Coach " + user.getUsername() + "!!");
    }
}
