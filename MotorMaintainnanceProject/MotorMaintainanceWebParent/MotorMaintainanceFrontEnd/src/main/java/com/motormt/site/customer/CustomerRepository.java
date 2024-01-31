package com.motormt.site.customer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.motormt.common.entity.Account;

public interface CustomerRepository extends CrudRepository<Account, Integer>{

	@Query("SELECT a FROM Account a WHERE a.email = ?1")
	public Account findByEmail(String email);
	
	@Query("SELECT a FROM Account a WHERE a.tendangnhap = :tendangnhap")
	public Account getAccountByTendangnhap(@Param("tendangnhap") String tendangnhap);
	
	@Query("SELECT a FROM Account a WHERE a.email = :email")
	public Account getAccountByEmail(@Param("email") String email);
	
	@Query("SELECT a FROM Account a WHERE a.email = :email")
	public List<Account> findAccountByEmail(@Param("email") String email);
}
