package com.example.arcface.config;


import com.example.arcface.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;


public class JwtUserFactory {
    private JwtUserFactory(){}

    public static JwtUser createUser(User user)
    {
        return new JwtUser(
                user.getWorkNumber(),
                user.getPassword(),
               mapToGrantedAuthorities( asList(user.getRole())),
                user.getLastPasswordResetDate()
        );
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {

        return authorities.stream()
                .map(SimpleGrantedAuthority::new )
                .collect(Collectors.toList());
    }
}
