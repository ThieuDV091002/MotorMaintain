package com.motormt.site.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.motormt.common.entity.Account;

public class CustomerUserDetails implements UserDetails {

	private Account account;
	
	
	public CustomerUserDetails(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if ("Người dùng".equals(account.getVaitro())) {
		authorities.add(new SimpleGrantedAuthority("Người dùng"));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return account.getMatkhau();
	}

	@Override
	public String getUsername() {
		return account.getTendangnhap();
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
		return account.isTrangthai();
	}
	
	public String getHoten() {
		return account.getHoten();
	}
	
	public String getEmail() {
		return account.getEmail();
	}
	
	public void setHoten(String hoten) {
		this.account.setHoten(hoten);
	}

}
