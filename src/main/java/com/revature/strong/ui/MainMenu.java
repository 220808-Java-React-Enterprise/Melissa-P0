package com.revature.strong.ui;

import com.revature.strong.daos.OrderDetailDAO;
import com.revature.strong.models.Equipment;
import com.revature.strong.models.OrderDetails;
import com.revature.strong.models.User;
import com.revature.strong.services.EquipmentService;
import com.revature.strong.services.OrderDetailService;
import com.revature.strong.services.UserService;

import java.math.BigDecimal;
import java.util.*;

public class MainMenu implements IMenu {
    private final User user;
    private final UserService userService;
    private final EquipmentService equipmentService;
    private final OrderDetailService orderdetailservice;

    public MainMenu(User user, UserService userService, EquipmentService equipmentService, OrderDetailService orderdetailservice) {
        this.user = user;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.orderdetailservice = orderdetailservice;
    }

    @Override
    public void start() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        System.out.println("\nWelcome to Your STRONGest Self, " + user.getUsername() + "!\n");

        exit:
            while(true) {

                System.out.println("Please select from the menu below:\n \n[1]Shop\n[2]View messages from Coach\n[3]View workout plan\n[4}Order History\n[X]Log out\n");
                input = scan.nextLine();


                switch (input){
                    case "1":
                        shop();
                        break;
                    case "2":
                        System.out.println("2 needs to be implemented");
                        break;
                    case "3":
                        System.out.println("3 needs to be implemented");
                        break;
                    case "4":
                        System.out.println("Order History:\n");
                        for (OrderDetails e : orderdetailservice.findOrdersByUserId(user.getId())){
                            System.out.println("Item: " + e.getEqname() + " || Qty: " + e.getQuantity() + " || total: $" + e.getSubtotal());
                        }
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
        String input = "";
        List<OrderDetails> toBuy = new ArrayList<>();
        Random rand = new Random();
        BigDecimal subTotal = BigDecimal.valueOf(0);
        OrderDetailDAO orderDAO = new OrderDetailDAO();
        OrderDetailService orderservice = new OrderDetailService(orderDAO);


        exit:
            while(true){
                System.out.println("Please select an ID to add to your cart");
                for (Equipment e : equipmentService.getAllEquipment()){
                    System.out.println("Id: " + e.getId() + " || Name: " + e.getName() + " || Price: $" + e.getPrice());
                }
                input = scan.nextLine();
                Equipment equip = equipmentService.findEquipmentByID(input);
                System.out.println("Did you select " + equip.getName() + "?\n y/n");
                input = scan.nextLine();
                if (input.toLowerCase().equals("y")){
                    System.out.println("How many: ");
                    input = scan.nextLine();
                    int quantity = Integer.parseInt(input);
                    int temp = (rand.nextInt(1000) + 1);
                    String id = String.valueOf(temp);
                    if (quantity%1 ==0){
                        BigDecimal price = equip.getPrice();
                        BigDecimal total = BigDecimal.valueOf(quantity).multiply(price);
                        BigDecimal quant = BigDecimal.valueOf(quantity);
                        toBuy.add(new OrderDetails(id,user.getId(), equip.getId(), equip.getName(), quant, total));

                        System.out.println("Item: " + equip.getName() + " has been added to your cart.  Your total is: $" + total);
                        subTotal = subTotal.add(total);
                        System.out.println("Would you like to keep shopping?\ny/n");
                        input = scan.nextLine();
                        if(input.toLowerCase().equals("y")){
                        } else {
                            System.out.println("Thank you for shopping with Strong!\n" +
                                    "\nYour final order is: ");
                            for(OrderDetails e : toBuy){
                                System.out.println("Item: " + e.getEqname() + "|| Qty: " + e.getQuantity());
                                orderservice.saveOrder(new OrderDetails(e.getId(), user.getId(), e.getEquipment_id(), e.getEqname(), e.getQuantity(), e.getSubtotal()));
                            }
                            System.out.println("Your order has been submitted\n" +
                                    "Your total is: $" + subTotal + "\nGoodbye!");
                            break exit;
                        }
                    }
                    else if (input.toLowerCase().equals("n")){
                        System.out.println("Please try again");
                        break;
                    } else {
                        System.out.println("Invalid Input.  Try again");
                        break;
                    }

                }

            }

    }
}
