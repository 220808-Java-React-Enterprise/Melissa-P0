package com.revature.strong.daos;

import com.revature.strong.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CrudDAO<User>{
    String path = "src/main/resources/db/user.txt";

    @Override
    public void save(User obj) throws IOException {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file, true);
            fw.write(obj.toFileString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("\n An error occurred when writing to a file.");
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

    public List<String> getAllUsernames(){
        List<String> usernames = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String userData = ""; //uuid:username:password:role
            while ((userData = br.readLine()) != null) {
                String[] userArr = userData.split(":"); //uuid, username, password, role
                usernames.add(userArr[1]);
            }
        } catch (FileNotFoundException e){
            throw new RuntimeException("An error occurred when trying to access the file.");
        } catch (IOException e) {
            throw new RuntimeException("\n An error occurred when writing to a file.");
        }
        return usernames;
    }
}
