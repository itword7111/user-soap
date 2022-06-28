package com.example.usersoap.service;

import com.techprimers.spring_boot_soap_example.User;
import com.techprimers.spring_boot_soap_example.UserWithRoles;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    com.techprimers.spring_boot_soap_example.UserWithRoles getUserWithRolesByLogin(String userLogin);

    void deleteUserByLogin(String login);

    List<String> addUser(UserWithRoles userWithRoles);

    List<String> updateUser(String login, UserWithRoles userWithRoles);
}
