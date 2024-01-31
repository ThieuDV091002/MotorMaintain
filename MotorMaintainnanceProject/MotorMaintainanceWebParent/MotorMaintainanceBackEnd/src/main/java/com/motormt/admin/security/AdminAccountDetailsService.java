package com.motormt.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.motormt.admin.account.AccountRepository;
import com.motormt.common.entity.Account;

public class AdminAccountDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String tendangnhap) throws UsernameNotFoundException {
		Account account = repo.getAccountByTendangnhap(tendangnhap);
		if(account != null) {
			return new AdminAccountDetails(account);
		}
		throw new UsernameNotFoundException("Không tìm thấy tài khoản với tên đăng nhập" + tendangnhap);
	}

}
