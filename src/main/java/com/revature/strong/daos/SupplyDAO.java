package com.revature.strong.daos;

import com.revature.strong.models.Equipment;
import com.revature.strong.models.Supply;
import com.revature.strong.utils.custom_exceptions.InvalidSQLException;
import com.revature.strong.utils.database.ConnectionFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplyDAO implements CrudDAO<Supply> {


    @Override
    public void save(Supply obj) throws IOException {

    }

    @Override
    public void update(Supply obj) {

    }

    @Override
    public void delete(Supply obj) {

    }

    @Override
    public Supply getById(String id) {
        return null;
    }

    @Override
    public List<Supply> getAll() {
        return null;
    }

    public void updateByEqname(String eqname, BigDecimal quantity){
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            BigDecimal currQuantity = getQuantity(eqname);

            PreparedStatement ps = con.prepareStatement("UPDATE supply SET quantity = ? WHERE eqname = ?");
            BigDecimal newVal = currQuantity.subtract(quantity);

            ps.setBigDecimal(1, newVal);
            ps.setString(2, eqname);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to update to the database");
        }
    }

    public BigDecimal getQuantity(String eqname){
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (quantity) FROM supply WHERE eqname = ?");
            ps.setString(1, eqname);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getBigDecimal("quantity");

        } catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when trying to save to the database");
        }

        return null;
    }
}
