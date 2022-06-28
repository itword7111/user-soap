package com.example.usersoap.validation;

import com.techprimers.spring_boot_soap_example.UserWithRoles;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {
    public List<String> validation(UserWithRoles userWithRoles){
        List<String> errors = new ArrayList<>();
        if (userWithRoles.getLogin().equals("")){
            errors.add("empty login");
        }
        if (userWithRoles.getName().equals("")){
            errors.add("empty name");
        }
        if (userWithRoles.getPassword().equals("")){
            errors.add("empty password");
        }
        else {
            if (!userWithRoles.getPassword().matches("(.)*\\d(.)*")){
                errors.add("password does not contain number");
            }
            if (!userWithRoles.getPassword().matches("(.)*[A-Z](.)*")){
                errors.add("password does not contain a capital letter");
            }
        }
        return errors;
    }
}
