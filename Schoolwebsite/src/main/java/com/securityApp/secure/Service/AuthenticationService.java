package com.securityApp.secure.Service;

import com.securityApp.secure.Models.AuthenticationRequest;
import com.securityApp.secure.Models.AuthenticationResponse;
import com.securityApp.secure.Models.User;
import com.securityApp.secure.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    public String authenticate(AuthenticationRequest request){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUserName(), request.getUserPassword()
        ));
        if(authenticate.isAuthenticated()){
            User user = userRepository.findByUserName(request.getUserName()).orElseThrow();
            System.out.println(jwtService.generateToken(user));
            return jwtService.generateToken(user);
        }else {
            throw new UsernameNotFoundException("invalid user");
        }

    }
}
