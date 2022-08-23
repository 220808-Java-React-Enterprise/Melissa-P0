package com.revature.strong.services;

import com.revature.strong.daos.OrderDetailDAO;
import com.revature.strong.models.OrderDetails;

import java.io.IOException;

public class OrderDetailService {

    private final OrderDetailDAO orderdetailDAO;

    public OrderDetailService(OrderDetailDAO orderdetailDAO){
        this.orderdetailDAO = orderdetailDAO;
    }

    public void saveOrder(OrderDetails order){
        orderdetailDAO.save(order);
    }


}
