package com.example.demo.Service;


import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    public List<User> findAllUsers();

    public User findUserByID(Integer id);

    public boolean addUser(User user);

    public boolean deleteUser(Integer id);

}
