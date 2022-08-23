package com.revature.strong.ui;

import com.revature.strong.daos.OrderDetailDAO;
import com.revature.strong.models.Equipment;
import com.revature.strong.models.OrderDetails;
import com.revature.strong.models.User;
import com.revature.strong.services.EquipmentService;
import com.revature.strong.services.OrderDetailService;
import com.revature.strong.services.SupplyService;
import com.revature.strong.services.UserService;
import com.revature.strong.utils.custom_exceptions.InvalidUserException;

import javax.xml.bind.SchemaOutputResolver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class MainMenu implements IMenu {
    private final User user;
    private final UserService userService;
    private final EquipmentService equipmentService;
    private final OrderDetailService orderdetailservice;
    private final SupplyService supplyservice;

    public MainMenu(User user, UserService userService, EquipmentService equipmentService, OrderDetailService orderdetailservice, SupplyService supplyservice) {
        this.user = user;
        this.userService = userService;
        this.equipmentService = equipmentService;
        this.orderdetailservice = orderdetailservice;
        this.supplyservice = supplyservice;
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
                        orderHistory();
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
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        java.sql.Date sqlDate = new java.sql.Date(today.getTime());


        exit:
            while(true){
                System.out.println("Please select an ID to add to your cart");
                for (Equipment e : equipmentService.getAllEquipment()){
                    System.out.println("Id: " + e.getId() + " || Name: " + e.getName() + " || Price: $" + e.getPrice());
                }
                input = scan.nextLine();  //Implement validation - is input an actual Equipment id?!!!
                Equipment equip = equipmentService.findEquipmentByID(input);
                System.out.println("Did you select " + equip.getName() + "?\ny/n");
                input = scan.nextLine();
                if (input.toLowerCase().equals("y")) {
                    System.out.println("How many: ");
                    input = scan.nextLine();
                    int quantity = Integer.parseInt(input);
                    int temp = (rand.nextInt(1000) + 1);
                    String id = String.valueOf(temp);
                    if (quantity % 1 == 0) {
                        BigDecimal price = equip.getPrice();
                        BigDecimal total = BigDecimal.valueOf(quantity).multiply(price);
                        BigDecimal quant = BigDecimal.valueOf(quantity);
                        BigDecimal amtAvail = supplyservice.getQuantityByEqname(equip.getName());
                        BigDecimal amtLeft = amtAvail.subtract(quant);
                        if(amtLeft.compareTo(BigDecimal.ZERO) < 0){
                            System.out.println("\nSorry we don't have enough in stock!  Please message your coach to restock!\n");
                        } else {
                            toBuy.add(new OrderDetails(id, user.getId(), today, equip.getId(), equip.getName(), quant, total));

                            System.out.println("Item: " + equip.getName() + " has been added to your cart.  Your total is: $" + total);
                            subTotal = subTotal.add(total);
                            System.out.println("Would you like to keep shopping?\ny/n");
                            input = scan.nextLine();
                            if (input.toLowerCase().equals("y")) {
                            } else {
                                System.out.println("Thank you for shopping with Strong!\n" +
                                        "\nYour final order is: ");
                                for (OrderDetails e : toBuy) {
                                    System.out.println("Item: " + e.getEqname() + "|| Qty: " + e.getQuantity());
                                    orderservice.saveOrder(new OrderDetails(e.getId(), user.getId(), today, e.getEquipment_id(), e.getEqname(), e.getQuantity(), e.getSubtotal()));
                                    supplyservice.updateByEqname(e.getEqname(), e.getQuantity(), false);
                                }
                                System.out.println("Your order has been submitted\n" +
                                        "Your total is: $" + subTotal + "\nGoodbye!\n");
                                break exit;
                            }
                        }
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

    public void orderHistory(){
        Scanner scan = new Scanner(System.in);
        String input = "";


        exit:
            while(true){
                System.out.println("How would you like to view your Order History?\n[1]Order History\n[2]Order History by date\n[3]Order History by price\n[X]Exit");
                input = scan.nextLine();

                switch(input){
                    case "1":
                        System.out.println("Order History:\n");
                        try{
                            for (OrderDetails e : orderdetailservice.findOrdersByUserId(user.getId())){
                                System.out.println("Date: " + e.getOddate() + " || Item: " + e.getEqname() + " || Qty: " + e.getQuantity() + " || total: $" + e.getSubtotal());
                            }

                            }catch(InvalidUserException e){
                            System.out.println("Sorry there are no orders for this user");
                        }
                        System.out.println();
                        break;
                    case "2":
                        System.out.println("Order History by Date:\n");
                        System.out.println("[1]Ascending\n[2]Descending");
                        String choice = scan.nextLine();
                        try{
                        List<OrderDetails> ordersList = orderdetailservice.sortOrdersByDate(user.getId());

                        if (choice.equals("1")) {
                            for (OrderDetails e : ordersList) {
                                System.out.println("Date: " + e.getOddate() + " || Item: " + e.getEqname() + " || Qty: " + e.getQuantity() + " || total: $" + e.getSubtotal());
                            }
                            System.out.println();
                        } else {
                            Collections.reverse(ordersList);
                            for (OrderDetails e : ordersList) {
                                System.out.println("Date: " + e.getOddate() + " || Item: " + e.getEqname() + " || Qty: " + e.getQuantity() + " || total: $" + e.getSubtotal());
                            }
                            System.out.println();
                        }
                        }catch (InvalidUserException e){
                            System.out.println("Sorry there are no orders for this user\n");
                        }
                        break;
                    case "3":
                        System.out.println("Order History by price:\n");
                        System.out.println("[1]Ascending\n[2]Descending");
                        String choiceprice = scan.nextLine();
                        try {
                            List<OrderDetails> ordersListPrice = orderdetailservice.sortOrdersByPrice(user.getId());
                            if (choiceprice.equals("1")) {
                                for (OrderDetails e : ordersListPrice) {
                                    System.out.println("Date: " + e.getOddate() + " || Item: " + e.getEqname() + " || Qty: " + e.getQuantity() + " || total: $" + e.getSubtotal());
                                }
                                System.out.println();
                            } else {
                                Collections.reverse(ordersListPrice);
                                for (OrderDetails e : ordersListPrice) {
                                    System.out.println("Date: " + e.getOddate() + " || Item: " + e.getEqname() + " || Qty: " + e.getQuantity() + " || total: $" + e.getSubtotal());
                                }
                                System.out.println();
                            }
                        } catch (InvalidUserException e){
                            System.out.println("Sorry there are no orders for this user\n");
                        }
                        break;
                    case "x":
                        break exit;
                    default:
                        System.out.println("Invalid entry, Please try again");
                        break;
                }
            }
    }
}
