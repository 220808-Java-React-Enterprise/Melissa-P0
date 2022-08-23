package com.revature.strong.services;

import com.revature.strong.daos.SupplyDAO;
import com.revature.strong.models.Supply;

import java.math.BigDecimal;

public class SupplyService {

    private final SupplyDAO supplyDAO;

    public SupplyService(SupplyDAO supplyDAO){
        this.supplyDAO = supplyDAO;
    }

    public void updateByEqname(String eqname, BigDecimal quantity){
        supplyDAO.updateByEqname(eqname, quantity);
    }
}
