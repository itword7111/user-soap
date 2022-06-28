package com.example.usersoap.repository.impl;

import com.example.usersoap.entity.Role;
import com.example.usersoap.entity.User;
import com.example.usersoap.repository.UserRepository;
import com.techprimers.spring_boot_soap_example.UserWithRoles;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> getAllUsers(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(userRoot);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        List<User> postList = typedQuery.getResultList();
        return postList;
    }

    @Override
    public User getUserByLogin(String userLogin){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate userPredicate = criteriaBuilder.equal(userRoot.get("login"), userLogin);
        criteriaQuery.select(userRoot).where(userPredicate);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    @Override
    public void deleteUserByLogin(String userLogin){
        User user = getUserByLogin(userLogin);
        entityManager.remove(user);
    }
    private Optional<Role> findRoleByName(String name){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot = criteriaQuery.from(Role.class);
        Predicate rolePredicate = criteriaBuilder.equal(roleRoot.get("name"), name);
        criteriaQuery.select(roleRoot).where(rolePredicate);
        TypedQuery<Role> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList().stream().findFirst();
    }
    private List<Role> soapRolesToEntityRoles(List<com.techprimers.spring_boot_soap_example.Role> soapRoles){
        List<Role> roles =new ArrayList<>();
        for (com.techprimers.spring_boot_soap_example.Role soapRole: soapRoles) {
            Optional<Role> role=findRoleByName(soapRole.getName());
            if(role.isPresent()){
                roles.add(role.get());
            }
            else {
                Role newRole=new Role(soapRole.getName());
                entityManager.persist(newRole);
                roles.add(newRole);
            }
        }
        return roles;
    }

    @Override
    public void addUser(UserWithRoles userWithRoles){
        List<Role> roles = soapRolesToEntityRoles(userWithRoles.getRoles());
        User user =new User(userWithRoles.getLogin(),userWithRoles.getName(),userWithRoles.getPassword(),roles);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(String userLogin, UserWithRoles userWithRoles){
        User user = getUserByLogin(userLogin);
        if(!user.getLogin().equals(userWithRoles.getLogin())){
            entityManager.remove(user);
            user=new User();
        }
        user.setName(userWithRoles.getName());
        user.setLogin(userWithRoles.getLogin());
        user.setPassword(userWithRoles.getPassword());
        user.setRoles(soapRolesToEntityRoles(userWithRoles.getRoles()));
        entityManager.persist(user);
    }
}
