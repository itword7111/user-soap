package com.example.usersoap.repository;

import com.example.usersoap.entity.User;
import com.techprimers.spring_boot_soap_example.UserWithRoles;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    User getUserByLogin(String userLogin);

    void deleteUserByLogin(String userLogin);

    void addUser(UserWithRoles userWithRoles);

    void updateUser(String userLogin, UserWithRoles userWithRoles);
}
