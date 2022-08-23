package com.revature.strong.ui;

import com.revature.strong.models.Equipment;
import com.revature.strong.models.Supply;
import com.revature.strong.models.User;
import com.revature.strong.services.EquipmentService;
import com.revature.strong.services.SupplyService;
import com.revature.strong.services.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CoachMenu implements IMenu {
    private final User user;
    private final UserService userService;
    private final EquipmentService equipmentService;
    private final SupplyService supplyservice;

    public CoachMenu(User user, UserService userService, EquipmentService equipmentService, SupplyService supplyservice) {
        this.user = user;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.supplyservice = supplyservice;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nWelcome to Your STRONGest self, Coach " + user.getUsername() + "!!");
        String input = "";

        exit:
            while (true){
                System.out.println("Please select from the menu below:\n \n[1]View Clients\n[2]Send Message\n[3]Replenish Inventory\n[x]Exit");
                input = scan.nextLine();

                switch(input){
                    case "1":
                        viewClients();
                        break;
                    case "2":
                        break;
                    case "3":
                        replenish();
                        break;
                    case "x":
                        System.out.println("Keep hookin and jabbin! See you next time!");
                        break exit;
                    default:
                        System.out.println("Invalid input, please try again");
                        break;
                }
            }
    }

    public void replenish(){
        Scanner scan = new Scanner(System.in);
        String input = "";

        exit:
            while(true) {
                System.out.println("Please select an ID to replenish to inventory");
                for (Supply s : supplyservice.getAll()) {
                    System.out.println("Id: " + s.getId() + " || Name: " + s.getEqName() + " || Qty: " + s.getQuantity());
                }
                input = scan.nextLine();
                Supply sup = supplyservice.getById(input);
                System.out.println("Did you select " + sup.getEqName() + "?\n y/n");
                input = scan.nextLine();
                if (input.toLowerCase().equals("y")) {
                    System.out.println("How many would you like to reorder: ");
                    input = scan.nextLine();
                    int quantity = Integer.parseInt(input);
                    BigDecimal quant = BigDecimal.valueOf(quantity);
                    supplyservice.updateByEqname(sup.getEqName(), quant, true);
                    System.out.println(sup.getEqName() + " replenished!  Would you like to replenish more?\ny/n");
                    input = scan.nextLine();
                    if (input.toLowerCase().equals("y")){
                    } else {
                        System.out.println("Thanks for restocking!");
                        break exit;
                    }

                } else if (input.toLowerCase().equals("n")) {
                    System.out.println("Please try again");
                    break;
                } else {
                    System.out.println("Invalid input, please try again");
                    break;
                }
            }
    }

    public void viewClients(){
        List<User> users = userService.getAllUsers();
        String input = "";
        String client = "";
        Scanner scan = new Scanner(System.in);

        exit:
        while(true){
            System.out.println("Please select from the menu below:\n[1]View All\n[2]View all Coaches\n[3]View all Clients\n[4]Search Client by name\n[X]Exit");
            input = scan.nextLine();
            switch(input){
                case "1":
                    for(User u : users){
                        System.out.println("Username: " + u.getUsername());
                    }
                    System.out.println();
                    break;
                case "2":
                    for(User u : users){
                        if(u.getCoach() == true) System.out.println("Username: " + u.getUsername());
                    }
                    System.out.println();
                    break;
                case "3":
                    for(User u : users){
                        if(u.getCoach() == false) System.out.println("Username: " + u.getUsername());
                    }
                    System.out.println();
                    break;
                case "4":
                    System.out.println("Clients name: ");
                    client = scan.nextLine();
                    User temp = userService.getUserByUsername(client);
                    if(temp ==null){
                        System.out.println("Sorry user doesn't exist");
                    } else{
                        System.out.println("Id: " + temp.getId() + " Name: " + temp.getUsername());
                    }
                    System.out.println();
                    break;
                case "x":
                    System.out.println("Keep crushing it! See you later!");
                    break exit;
                default:
                    System.out.println("Invalid input, Please try again");
                    break ;

            }


        }
    }
}
