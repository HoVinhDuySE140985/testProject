package com.example.demo.security;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepo.findUserByEmail(email);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    mapRolesAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid user or password");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesAuthorities(List<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return mapRoles;
    }

}
