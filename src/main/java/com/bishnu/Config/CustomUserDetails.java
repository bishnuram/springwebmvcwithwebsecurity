package com.bishnu.Config;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bishnu.Entity.AppUser;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String fname;
    private String lname;
    private int id;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(AppUser appUser) {
        this.username = appUser.getUsername();
        this.password = appUser.getPassword();
        this.fname = appUser.getFname();
        this.lname = appUser.getLname();
        
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
