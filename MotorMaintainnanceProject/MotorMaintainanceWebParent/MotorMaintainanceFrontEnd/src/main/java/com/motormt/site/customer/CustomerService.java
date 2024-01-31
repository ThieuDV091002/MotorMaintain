package com.motormt.site.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.motormt.common.entity.Account;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean isEmailUnique(String email) {
		Account accountByEmail = repo.getAccountByEmail(email);
		return accountByEmail == null;
	}
	
	public boolean isMailUnique(String email) {
		List<Account> list = repo.findAccountByEmail(email);
		return list.size() > 1;
	}
	
	public void save(Account account) {
		account.setVaitro("Người dùng");
		account.setTrangthai(true);
		encodePassword(account);
		repo.save(account);	
	}

	private void encodePassword(Account account) {
		String encodedPassword = passwordEncoder.encode(account.getMatkhau());
		account.setMatkhau(encodedPassword);
	}

	public Account getAccountByEmail(String email) {
	    return repo.getAccountByEmail(email);
	}
	
	public Account getAccountByUsername(String username) {
	    return repo.getAccountByTendangnhap(username);
	}
	
	public Account updateAccount(Account accountInForm) {
		Account accountInDB = repo.findById(accountInForm.getIDtaikhoan()).get();
		if(!accountInDB.getMatkhau().isEmpty()) {
			accountInDB.setMatkhau(accountInForm.getMatkhau());
			encodePassword(accountInDB);
		}
		accountInDB.setIDtaikhoan(accountInForm.getIDtaikhoan());
		accountInDB.setHoten(accountInForm.getHoten());
		accountInDB.setTendangnhap(accountInForm.getTendangnhap());
		accountInDB.setEmail(accountInForm.getEmail());
		accountInDB.setSodienthoai(accountInForm.getSodienthoai());
		accountInDB.setDiachi(accountInForm.getDiachi());
		accountInDB.setVaitro(accountInForm.getVaitro());
		accountInDB.setTrangthai(accountInForm.isTrangthai());
		return repo.save(accountInDB);
	}
}
