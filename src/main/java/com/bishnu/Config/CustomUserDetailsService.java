package com.bishnu.Config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bishnu.Entity.AppUser;
import com.bishnu.Repository.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
@Autowired
private UserRepo userRepo;
@Override 
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
	AppUser appUser = userRepo.findUserByUsername(username);
	if (appUser == null) {
        throw new UsernameNotFoundException("User not found");
    }
	return new CustomUserDetails(appUser);
//    return org.springframework.security.core.userdetails.User
//            .withUsername(appUser.getUsername())
//            .password(appUser.getPassword())
//            .authorities("USER") // You can customize authorities/roles here
//            .build();
}
	}