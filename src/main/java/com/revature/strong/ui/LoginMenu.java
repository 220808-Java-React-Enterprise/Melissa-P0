package com.revature.strong.ui;

import com.revature.strong.models.User;
import com.revature.strong.services.UserService;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class LoginMenu implements IMenu {
    private final UserService userService;

    public LoginMenu(UserService userService){
        this.userService = userService;
    }

    @Override
    public void start() {
        String userInput = "";
        Scanner scan = new Scanner(System.in);

        exit:
        {
            while (true) {

                System.out.println("\nWelcome to your STRONGest self!");
                System.out.println("[1] Login");
                System.out.println("[2] Signup");
                System.out.println("[X] Exit");
                System.out.print("Please select an option: ");

                userInput = scan.nextLine();

                System.out.println("You selected " + userInput + "\n");

                switch (userInput) {
                    case "1":
                        login();
                        break;
                    case "2":
                        User user = signup();
                     //   userService.register(user);
                        new MainMenu(user).start();
                        break;
                    case "x":
                        System.out.println("Thank you for visiting, come again!");
                        break exit;
                    default:
                        System.out.println("\nInvalid input");
                        break;
                }
            }
        }
    }

    private void login() {
        System.out.println("needs to be implemented!");
        List<String> usernames = userService.getAllUsernames();

        System.out.println(usernames.contains("melissa"));

    }

    private User signup() {
        Scanner scan = new Scanner(System.in);
        String username = "";
        String password = "";
        User user = new User();

        exit:
        {
            while (true) {
                usernameExit:
                {
                    while (true) {
                        System.out.println("\nPlease enter your username: ");
                        username = scan.nextLine();

                        try {
                            userService.isValidUsername(username);
                            break usernameExit;
                        } catch (InvalidUserException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                passwordExit:
                {
                    while (true) {
                        System.out.println("Please enter you password: ");
                        password = scan.nextLine();

                        try {
                            userService.isValidPassword(password);
                            break passwordExit;
                        } catch (InvalidUserException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                confirmExit:
                {
                    while (true) {
                        System.out.println("\nIs this correct? (y/n):");
                        System.out.println("Username: " + username + "\nPassword: " + password);

                        switch (scan.nextLine().toLowerCase()) {
                            case "y":
                                System.out.println("Creating Account.....");
                                User newUser = new User(username, password, UUID.randomUUID().toString());
                                return newUser;
                            case "n":
                                System.out.println("oh dear!");
                                break confirmExit;
                            default:
                                System.out.println("\nInvalid input");
                                break;
                        }
                    }
                }
            }

        }
    }
}
