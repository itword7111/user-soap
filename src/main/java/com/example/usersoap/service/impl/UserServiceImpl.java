package com.example.usersoap.service.impl;

import com.example.usersoap.entity.Role;
import com.example.usersoap.entity.User;
import com.example.usersoap.repository.UserRepository;

import com.example.usersoap.service.UserService;
import com.example.usersoap.validation.UserValidator;
import com.techprimers.spring_boot_soap_example.UserWithRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserValidator validator;
    @Override
    public List<com.techprimers.spring_boot_soap_example.User> getAllUsers(){
        List<com.techprimers.spring_boot_soap_example.User> listOfSOAPUsers=new ArrayList<>();
        for (User user:repository.getAllUsers()) {
            com.techprimers.spring_boot_soap_example.User userSOAP = new com.techprimers.spring_boot_soap_example.User();
            userSOAP.setLogin(user.getLogin());
            userSOAP.setName(user.getName());
            userSOAP.setPassword(user.getPassword());
            listOfSOAPUsers.add(userSOAP);
        }
        return listOfSOAPUsers;
    }

    @Override
    public com.techprimers.spring_boot_soap_example.UserWithRoles getUserWithRolesByLogin(String userLogin){
        List<com.techprimers.spring_boot_soap_example.UserWithRoles> listOfSOAPUsers=new ArrayList<>();
        User user= repository.getUserByLogin(userLogin);
        com.techprimers.spring_boot_soap_example.UserWithRoles userSOAP = new com.techprimers.spring_boot_soap_example.UserWithRoles();
        userSOAP.setLogin(user.getLogin());
        userSOAP.setName(user.getName());
        userSOAP.setPassword(user.getPassword());
        List<com.techprimers.spring_boot_soap_example.Role> soapRoles =new ArrayList<>();
        for (Role role:user.getRoles()) {
            com.techprimers.spring_boot_soap_example.Role soapRole=new com.techprimers.spring_boot_soap_example.Role();
            soapRole.setName(role.getName());
            soapRoles.add(soapRole);
        }
        userSOAP.getRoles().addAll(soapRoles);
        return userSOAP;
    }

    @Override
    public void deleteUserByLogin(String login){
        repository.deleteUserByLogin(login);
    }

    @Override
    public List<String> addUser(UserWithRoles userWithRoles){
        List<String> errors = validator.validation(userWithRoles);
        if(errors.size()==0){
            repository.addUser(userWithRoles);
        }
        return errors;
    }

    @Override
    public List<String> updateUser(String login, UserWithRoles userWithRoles){
        List<String> errors = validator.validation(userWithRoles);
        if(errors.size()==0){
            repository.updateUser(login, userWithRoles);
        }
        return errors;
    }
}
