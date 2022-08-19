package com.revature.strong.daos;

import com.revature.strong.models.User;
import com.revature.strong.utils.database.ConnectionFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO implements CrudDAO<User>{

    @Override
    public void save(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (id, username, userpassword, coach, coach_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getUserPassword());
            ps.setBoolean(4, obj.getCoach());
            ps.setString(5, obj.getCoach_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when trying to save to the database");
        }
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    public String getUsername(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (username) FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("username");

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when trying to save to the database");
        }

        return null;
    }

    public List<String> getAllUsernames(){
        return null;
    }
}
