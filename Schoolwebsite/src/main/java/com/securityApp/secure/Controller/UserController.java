package com.securityApp.secure.Controller;

import com.securityApp.secure.Models.User;
import com.securityApp.secure.Service.UsersService;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostConstruct
    private void assignRoleTable(){
        usersService.assignRoleTable();
    }


    @PostMapping("/registration")
    public User registerNewUser(
            @RequestParam("userName") String userName,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("userPassword") String password,
            @RequestParam("photo") MultipartFile photo
    )throws Exception{

            System.out.println("Received parameters: " + userName + ", " + firstName + ", " + lastName + ", " + password);

            return usersService.RegisterUser(userName, firstName, lastName, password, photo);

    }

    @GetMapping("/allUsers")
    private List<User> findAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public Page<User> findCandidateByPagination(@PathVariable Integer offset, @PathVariable Integer pageSize){
        return usersService.findUserByPagination(offset,pageSize);}


    @GetMapping("/findByUsername/{userName}")
    private User findUser(@PathVariable String userName){
        return usersService.findUserByUserName(userName);
    }


    @DeleteMapping("/deleteUser/{userName}")
    public void deleteUser(@PathVariable String userName){
        usersService.deleteUser(userName);

    }

    @PutMapping("/updateUser/{username}")
    public User updateUser(@PathVariable String username,@RequestBody User user) {
        return usersService.update(username,user);
    }

    @PostMapping("/forAdmin")
    private String forAdmin(){
        return "welcome Admin";
    }

    @PostMapping("/forUser")
    private String forUser(){
        return "welcome user";
    }



}
