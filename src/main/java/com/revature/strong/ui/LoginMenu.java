package com.revature.strong.ui;

import com.revature.strong.daos.UserDAO;
import com.revature.strong.models.User;
import com.revature.strong.services.UserService;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import java.io.IOException;
import java.util.*;

public class LoginMenu implements IMenu {
    private final UserService userService;

    public LoginMenu(UserService userService) {
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
                        start();    //Implement better - after login with username and password it SHOULD NOT take you back to start() only if you can't remember username and password
                        break exit;
                    case "2":
                        User user = signup();
                        if (user == null) {
                            start();
                        } else {
                            userService.register(user);
                            new MainMenu(user, new UserService(new UserDAO())).start();
                        }
                        break exit;
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
        Scanner scan = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("\nLog in:");

        exit:
        {
            while (true) {
                    System.out.println("\nPlease enter your username: ");  //IMPLEMENT: x to break out of loop if they don't know username or login
                    username = scan.nextLine();

                    System.out.println("\nPlease enter your password: \nIf you forgot your username or password enter x to exit");
                    password = scan.nextLine();
                if (password.toLowerCase().equals("x")) break exit;

                    try {
                        User user = userService.login(username, password);
                        if (user.getCoach().equals(Boolean.TRUE)) new CoachMenu(user, new UserService(new UserDAO())).start();
                        else new MainMenu(user, new UserService(new UserDAO())).start();
                        break exit;
                    } catch (InvalidUserException e) {
                        System.out.println(e.getMessage());
                    }
            }
        }
    }

    private User signup() {
        Scanner scan = new Scanner(System.in);
        String username = "";
        String password = "";
        String password2 = "";
        Boolean coach = false;
        User user;
        String coachId = null;

        exit:
        {
            while (true) {

                coachExit:
                {
                    while (true) {
                        System.out.println("Are you a coach? y/n");

                        switch (scan.nextLine().toLowerCase()) {
                            case "y":
                                coach = true;
                                break coachExit;
                            case "n":
                                coach = false;
                                break coachExit;
                            default:
                                System.out.println("\nInvalid input, please try again");
                                break;
                        }
                    }
                }


                usernameExit:
                {
                    while (true) {
                        System.out.println("Please enter your username: \nOr x to exit");
                        username = scan.nextLine();
                        if (username.toLowerCase().equals("x")) {
                            return null;
                        } else {
                            try {
                                userService.isValidUsername(username);
                                userService.isDuplicateUsername(username);
                                break usernameExit;
                            } catch (InvalidUserException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                }

                passwordExit:
                {
                    while (true) {
                        try {
                            System.out.println("Please enter your password: \nOr x to exit");
                            password = scan.nextLine();
                            if (password.toLowerCase().equals("x")) {
                                return null;
                            } else {
                                userService.isValidPassword(password);
                                break passwordExit;
                            }
                        } catch (InvalidUserException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                confirmExit:
                {
                    while (true) {
                        System.out.println("\nIs this correct? (y/n):");
                        System.out.println("Username: " + username + "\nPassword: " + password + "\nCoach: " + coach);

                        switch (scan.nextLine().toLowerCase()) {
                            case "y":
                                System.out.println("Creating Account.....");
                                User newUser = new User(username, password, UUID.randomUUID().toString(), coach, coachId);
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