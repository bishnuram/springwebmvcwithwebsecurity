package com.bishnu.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bishnu.Entity.AppUser;
@Repository
public interface UserRepo extends JpaRepository<AppUser, Integer> {
			AppUser	findUserByUsername(String username);
	
	
	
}