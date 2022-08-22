package com.revature.strong.services;

import com.revature.strong.daos.EquipmentDOA;
import com.revature.strong.models.Equipment;

import java.util.List;

public class EquipmentService {

    private final EquipmentDOA equipmentDOA;

    public EquipmentService (EquipmentDOA equipmentDOA) {
        this.equipmentDOA = equipmentDOA;
    }

    public List<Equipment> getAllEquipment() {

        return equipmentDOA.getAll();
    }

    /*public Equipment findEquipmentByID (String id){



    }*/
}
