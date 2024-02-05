package com.securityApp.secure.Service;

import com.securityApp.secure.Models.Role;
import com.securityApp.secure.Models.User;
import com.securityApp.secure.Models.UserRegistration;
import com.securityApp.secure.Repository.RoleRepository;
import com.securityApp.secure.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;


    public void assignRoleTable() {
        Optional<Role> roles = roleRepository.findByAuthority("ROLE_USER");
        if (roles.isEmpty()) {
            roleRepository.save(new Role("ROLE_USER"));
            roleRepository.save(new Role("ROLE_ADMIN"));

            System.out.println("created");
        } else {
            System.err.println("already create");
        }
    }

    public User RegisterUser( String userName,
                             String firstName,
                             String lastName,
                             String password,
                             MultipartFile photo) throws Exception{
        Role userRole = null;
        String designation;
//        mailService.sendPassword(userName,password);

            if(userName.equals("Admin") &&password.equals("Password")){
                userRole =  roleRepository.findByAuthority("ROLE_ADMIN").get();
                designation = "Admin";
            }else {
                userRole =  roleRepository.findByAuthority("ROLE_USER").get();
                designation = "Learner";}

            HashSet<Role> newRole = new HashSet<>();
            newRole.add(userRole);
            return   userRepository.save(new User(userName,firstName,lastName,designation,passwordEncoder.encode(password),photo.getBytes(),newRole));

    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Page<User> findUserByPagination(Integer offset, Integer pageSize){
        return userRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public User findUserByUserName(String userName){
        return userRepository.findByUserName(userName).get();
    }

    public void deleteUser(String userName) {
        User user = userRepository.findByUserName(userName).orElse(null);

        if (user != null && !user.getUserName().equals("Admin")) {
            user.getRole().clear();
            userRepository.save(user);

            userRepository.delete(user);
        }
    }


    public User update( String username,User user) {
        User foundUser = userRepository.findByUserName(username).get();

        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setPhoto(user.getPhoto());
        userRepository.save(foundUser);

        return user;
    }

    public Long userCount(){return userRepository.count();}

}