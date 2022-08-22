package com.revature.strong.ui;

import com.revature.strong.models.Equipment;
import com.revature.strong.models.User;
import com.revature.strong.services.EquipmentService;
import com.revature.strong.services.UserService;

import java.util.Scanner;

public class MainMenu implements IMenu {
    private final User user;
    private final UserService userService;
    private final EquipmentService equipmentService;

    public MainMenu(User user, UserService userService, EquipmentService equipmentService) {
        this.user = user;
        this.userService = userService;
        this.equipmentService = equipmentService;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);
        String input = "";


        exit:
            while(true) {

                System.out.println("\nWelcome to Your STRONGest Self, " + user.getUsername() + "!\n");
                System.out.println("Please select from the menu below: \n[1]Shop\n[2]View messages from Coach\n[3]View workout plan\n[4}Order History\n[X]Log out\n");
                input = scan.nextLine();


                switch (input){
                    case "1":
                        shop();
                        break exit;
                    case "2":
                        System.out.println("2 needs to be implemented");
                        break;

                    case "3":
                        System.out.println("3 needs to be implemented");
                        break;
                    case "4":
                        System.out.println("4 needs to be implemented");
                        break;
                    case "x":
                        System.out.println("Enjoy your workout! Come again!");
                        break exit;
                    default:
                        System.out.println("Invalid input! Please try again");
                        break;
                }
            }

    }

    public void shop(){
        Scanner scan = new Scanner(System.in);
        String input = ";";


        exit:
            while(true){
                System.out.println("Please select an ID to add to your cart\n");
                for (Equipment e : equipmentService.getAllEquipment()){
                    System.out.println("Id: " + e.getId() + " || Name: " + e.getName() + " || Price: $" + e.getPrice());
                }
                input = scan.nextLine();
                System.out.println("Did you select ");
            }

    }
}
