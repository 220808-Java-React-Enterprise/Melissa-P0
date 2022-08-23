package com.revature.strong.ui;

import com.revature.strong.models.Equipment;
import com.revature.strong.models.Supply;
import com.revature.strong.models.User;
import com.revature.strong.services.EquipmentService;
import com.revature.strong.services.SupplyService;
import com.revature.strong.services.UserService;

import java.math.BigDecimal;
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
}
