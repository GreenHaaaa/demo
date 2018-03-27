package com.example.arcface.config;

import com.example.arcface.domain.User;
import com.example.arcface.reposity.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JtwUserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private UserReposity userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> users = userRepository.findById(s);
        User user = new User();
        if(users.isPresent()) user = users.get();
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        } else {
            return JwtUserFactory.createUser(user);
        }
    }
}
