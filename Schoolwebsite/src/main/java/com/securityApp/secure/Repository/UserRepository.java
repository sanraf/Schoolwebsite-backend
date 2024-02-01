package com.securityApp.secure.Repository;

import com.securityApp.secure.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByUserName(String userName);
    User deleteByUserName(String userName);
}
