package com.bishnu.controller;


import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bishnu.Entity.AppUser;
import com.bishnu.Entity.RegisterDto;
import com.bishnu.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class myController {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	
@GetMapping("/")
public String openHome(){
		return "index";
	}	
	
@GetMapping("/profile")
public String openProfilePage(@AuthenticationPrincipal UserDetails userDetails,Model model) {
	model.addAttribute("currentUser",userDetails);
	Iterable<AppUser> user1= userService.searchAll();
    model.addAttribute("alluser",user1);
    System.out.println(user1);
    return "profile";
}

@GetMapping("/loginPage")
public String showLoginPage(Model model) {
model.addAttribute("RegisterDto", new RegisterDto());
	      return "login";
}


@GetMapping("/register")
public String showRegisterPage(Model model, HttpSession session) {
	model.addAttribute("registerDto", new RegisterDto());
	return "register"; 
}

//@GetMapping("/editUser")
//public String editview(){
//		return "editpage";
//}


@PostMapping("/register")
public String register(@Valid @ModelAttribute RegisterDto registerDto,BindingResult result, Model Model,HttpSession session) throws Exception{
	
if(result.hasErrors()){
	return "register";
}
AppUser existingUser= userService.searchExistingUser(registerDto.getUsername());
	if(existingUser!=null ){
		session.setAttribute("msg","This email address is already used");
		return "register"; // redirect losses data inputed earlier.
	} else
{ 
	AppUser newUser=new AppUser();
	newUser.setFname(registerDto.getFname());
	newUser.setLname(registerDto.getLname());
	newUser.setUsername(registerDto.getUsername());
	newUser.setMobile(registerDto.getMobile());
	newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
	newUser.setRole("USER");
	userService.registerNewUser(newUser);
	session.setAttribute("msg","User Registered Successfully");
   return "redirect:/loginPage";
   }
}

@GetMapping("/logout")
public String userLogout(HttpSession session) {
	session.invalidate();
	return "login";
}

@RequestMapping(path="/deleteUser/{delUserId}")
public String deleteUser(@PathVariable int delUserId,@AuthenticationPrincipal UserDetails currentUser, HttpSession session) 
{
int currentUserId=userService.searchExistingUser(currentUser.getUsername()).getId();
if(currentUserId==delUserId)
{ session.setAttribute("msg","Cannot delete current user");

}
else
{
userService.deleteUser(delUserId);
session.setAttribute("delmsg","User Deleted Successfully");

	Iterable< AppUser> user1= userService.searchAll();
    session.setAttribute("alluser",user1);
		
}return "redirect:/profile";
}

@RequestMapping(value="/editUser/{editUserId}")
public String editUserDetail(@PathVariable int editUserId, Model model) throws Exception
{	
	Optional<AppUser> editUserDetail=userService.findUserById(editUserId);
	model.addAttribute("registerDto",editUserDetail.get());
	return "editpage";
	}

@PostMapping("/updateUser")
public String UpdateUser(@Valid @ModelAttribute RegisterDto registerDto,BindingResult result, Model model,HttpSession session)
{ 
	
	if(result.hasErrors())
{
	return "editpage";
	
}
	AppUser checkExistingUser= userService.searchExistingUser(registerDto.getUsername());
		if(registerDto.getId()!=checkExistingUser.getId() && checkExistingUser.getUsername()!=null ){
			session.setAttribute("msg","Someone else has already used this email Id");
			return "editpage"; // redirect losses data inputed earlier.
		} else
	{ 
		AppUser newUser=new AppUser();
		newUser.setId(registerDto.getId());
		newUser.setFname(registerDto.getFname());
		newUser.setLname(registerDto.getLname());
		newUser.setUsername(registerDto.getUsername());
		newUser.setMobile(registerDto.getMobile());
		newUser.setPassword(registerDto.getPassword());
		newUser.setRole("USER");
		userService.registerNewUser(newUser);
		session.setAttribute("msg","User Details Updated Successfully");
	   return "redirect:/profile";
	   }
}

}
