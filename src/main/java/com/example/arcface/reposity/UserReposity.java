package com.example.arcface.reposity;

import com.example.arcface.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserReposity extends JpaRepository<User,String>{
    public User findUserByworkNumber(String id);
    public List<User> findAllByRole(String role);


}
