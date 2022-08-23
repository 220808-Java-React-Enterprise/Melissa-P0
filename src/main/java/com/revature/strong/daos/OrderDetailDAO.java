package com.revature.strong.daos;

import com.revature.strong.models.OrderDetails;
import com.revature.strong.utils.custom_exceptions.InvalidSQLException;
import com.revature.strong.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAO implements CrudDAO<OrderDetails> {


    @Override
    public void save(OrderDetails obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO orderdetails (id, user_id, equipment_id, eqname, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUserid());
            ps.setString(3, obj.getEquipment_id());
            ps.setString(4, obj.getEqname());
            ps.setBigDecimal(5, obj.getQuantity());
            ps.setBigDecimal(6, obj.getSubtotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }
    }

    @Override
    public void update(OrderDetails obj) {

    }

    @Override
    public void delete(OrderDetails obj) {

    }

    @Override
    public OrderDetails getById(String id) {
        return null;
    }

    @Override
    public List<OrderDetails> getAll() {
        return null;
    }
}
