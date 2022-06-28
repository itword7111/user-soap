package com.example.usersoap.endpoint;

import com.example.usersoap.service.UserService;
import com.techprimers.spring_boot_soap_example.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.List;

@Endpoint
public class UserEndpoint {
    @Autowired
    private UserService userService;
    @PayloadRoot(namespace = "http://techprimers.com/spring-boot-soap-example",
            localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUserRequest() {
        GetUserResponse response = new GetUserResponse();
        response.getUser().addAll(userService.getAllUsers());
        return response;
    }

    @PayloadRoot(namespace = "http://techprimers.com/spring-boot-soap-example",
            localPart = "getUserWithRolesRequest")
    @ResponsePayload
    public GetUserWithRolesResponse getUserWithRolesRequest(@RequestPayload GetUserWithRolesRequest request) {
        GetUserWithRolesResponse response = new GetUserWithRolesResponse();
        response.setUserWithRoles(userService.getUserWithRolesByLogin(request.getLogin()));
        return response;
    }

    @PayloadRoot(namespace = "http://techprimers.com/spring-boot-soap-example",
            localPart = "deleteUserWithRolesRequest")
    @ResponsePayload
    public void deleteUserRequest(@RequestPayload DeleteUserWithRolesRequest request) {
        userService.deleteUserByLogin(request.getLogin());
    }

    @PayloadRoot(namespace = "http://techprimers.com/spring-boot-soap-example",
            localPart = "addUserWithRolesRequest")
    @ResponsePayload
    public GetValidationResponse addUserWithRolesRequest(@RequestPayload AddUserWithRolesRequest request) {
        GetValidationResponse response = new GetValidationResponse();
        List<String> validationErrors = userService.addUser(request.getUserWithRoles());
        if (validationErrors.size()==0){
            response.setSuccess(true);
        }
        else {
            response.getErrors().addAll(validationErrors);
        }
        return response;
    }

    @PayloadRoot(namespace = "http://techprimers.com/spring-boot-soap-example",
            localPart = "updateUserWithRolesRequest")
    @ResponsePayload
    public GetValidationResponse updateUserWithRolesRequest(@RequestPayload UpdateUserWithRolesRequest request) {
        GetValidationResponse response = new GetValidationResponse();
        List<String> validationErrors = userService.updateUser(request.getLogin(), request.getUserWithRoles());
        if (validationErrors.size()==0){
            response.setSuccess(true);
        }
        else {
            response.getErrors().addAll(validationErrors);
        }
        return response;
    }
}
