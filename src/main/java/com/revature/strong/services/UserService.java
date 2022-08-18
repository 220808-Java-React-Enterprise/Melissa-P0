package com.revature.strong.services;

import com.revature.strong.daos.UserDAO;
import com.revature.strong.models.User;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;

import java.io.IOException;
import java.util.List;

public class UserService {
    //is the API, UI talks to Services and the services talk to the DAO
    //validate anything pertaining to user
    //replicate of models
    //dependency injection global variable
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void isValidUsername(String username) {
        if (!username.matches("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9]+(?<![_.])$")) {
            throw new InvalidUserException("\nUsername not Valid.\n*Username can only contain alphanumerics" +
                    "\n*Username must start with a capital letter" +
                    "\n*Username must contain a lowercase letter" +
                    "\n*Username must contain a number" +
                    "\n*Username must be 8-20 characters long");
        }
    }

    public void isValidPassword(String password) {
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            throw new InvalidUserException("\nPassword not Valid.\n*Password must have a minimum of 8 character" +
                    "\n*Password must have at least one letter" +
                    "\n*Password must have at least one number");

        }
    }

//    public void register(User user)  {
//            userDAO.save(user);
//    }

    public List<String> getAllUsernames() {
        return userDAO.getAllUsernames();
    }

}
