package com.motormt.site.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestcontroller {

	@Autowired
	private CustomerService service;
	
	@PostMapping("/customer-check_email")
	public String checkDuplicateEmail(@Param("email")String email) {
		return service.isEmailUnique(email) ? "OK" : "Duplicated";
	}
	
	@PostMapping("/customer-check_mail")
	public String checkDuplicatemail(@Param("email")String email) {
		return service.isMailUnique(email) ? "Duplicated" : "OK";
	}
}
