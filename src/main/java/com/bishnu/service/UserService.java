package com.bishnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bishnu.Entity.AppUser;
import com.bishnu.Entity.RegisterDto;
import com.bishnu.Repository.UserRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Transactional
	public AppUser searchExistingUser(String username)
	{
		AppUser user = userRepo.findUserByUsername(username);
		return user;
	    	        
	  }	
	
	@Transactional
 	public Iterable<AppUser> searchAll()
{ 
	Iterable<AppUser> user1=  userRepo.findAll();
    return user1;
}
	
	@Transactional
	public void deleteUser(Integer id)
	{
		userRepo.deleteById(id);
	}
	
	
	@Transactional
	public void registerNewUser(AppUser user) {
		userRepo.save(user);
		}
	
	@Transactional
	public void updateUser(AppUser user) {
		
		userRepo.save(user);
		}
	
	
	
	public Optional<AppUser> findUserById(Integer Id)
	{
		Optional<AppUser> userToEdit= userRepo.findById(Id);
		return userToEdit;
	}
	
	public void removeSessionMessage()
	{
		HttpSession session=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		session.removeAttribute("msg");
		
	}
	

}
